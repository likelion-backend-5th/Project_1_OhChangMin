package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items/{itemId}/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public String createChat(@PathVariable Long itemId, Authentication auth) {
        Long chatRoomId = chatService.createRoom(itemId, auth.getName());
        return "redirect:/items/" + itemId + "/chat/" + chatRoomId;
    }

    @GetMapping("/{chatId}")
    public String enterChat(@PathVariable Long itemId,
                            @PathVariable Long chatId,
                            Model model) {
        Chat chat = chatService.findChat(itemId, chatId);
        ChatResponse response = ChatResponse.createChatResponse(chat);
        model.addAttribute("chat", response);
        return "chat/room";
    }

    //TODO 다른 곳에 설정
    @Data
    private static class ChatResponse {

        private String ownerName;
        private String buyerName;

        public static ChatResponse createChatResponse(Chat chat) {
            ChatResponse response = new ChatResponse();
            response.setOwnerName(chat.getItem().getUser().getUsername());
            response.setBuyerName(chat.getBuyer().getUsername());
            return response;
        }
    }
}