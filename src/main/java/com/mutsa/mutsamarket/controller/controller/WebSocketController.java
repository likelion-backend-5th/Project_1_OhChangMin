package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{chatId}")
    public void sendMessage(@DestinationVariable Long chatId, ChatMessageRequest message, Authentication auth) {
        chatService.addChatMessage(chatId, auth.getName(), message.content);
        messagingTemplate.convertAndSend("/queue/" + chatId, new ChatMessageResponse(auth.getName(), message.content, LocalDateTime.now()));
    }

    private record ChatMessageRequest(String content) {
    }

    private record ChatMessageResponse(String username, String content, LocalDateTime time) {
    }
}
