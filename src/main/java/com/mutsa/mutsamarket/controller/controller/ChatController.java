package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.ChatMessage;
import com.mutsa.mutsamarket.service.ChatService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/items/{itemId}/chats")
    public String createChat(@PathVariable Long itemId, Authentication auth) {
        Long chatRoomId = chatService.createRoom(itemId, auth.getName());
        return "redirect:/chats/" + chatRoomId;
    }

    @GetMapping("/chats")
    public String chatList(Authentication auth,
                           Model model) {
        List<Chat> chats = chatService.findChats(auth.getName());
        model.addAttribute("chats", chats);
        return "chats/list";
    }

    @GetMapping("/chats/{chatId}")
    public String enterChat(@PathVariable Long chatId,
                            Authentication auth,
                            Model model) {
        Chat chat = chatService.findChat(chatId, auth.getName());
        List<ChatMessageResponse> messages = chat.getChatMessages()
                .stream()
                .map(ChatMessageResponse::createChatResponse).toList();

        model.addAttribute("messages", messages);
        return "chats/room";
    }

    //TODO 재배치
    @Data
    private static class ChatMessageResponse {

        private String username;
        private String content;
        private LocalDateTime time;

        public static ChatMessageResponse createChatResponse(ChatMessage chatMessage) {
            ChatMessageResponse response = new ChatMessageResponse();
            response.setUsername(chatMessage.getUsername());
            response.setContent(chatMessage.getContent());
            response.setTime(LocalDateTime.now());
            return response;
        }
    }
}