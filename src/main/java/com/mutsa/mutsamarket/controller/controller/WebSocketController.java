package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{chatId}")
    public void sendMessage(@DestinationVariable Long chatId, ChatMessage message) {
        chatService.addChatMessage(chatId, message.username, message.content);
        messagingTemplate.convertAndSend("/queue/" + chatId, new ChatMessage(message.username, message.content));
    }

//    @SubscribeMapping("/{chatId}")
//    public Response subscribe(@DestinationVariable("chatId") Long chatId) {
//        System.out.println("chatId = " + chatId);
//        return new Response("구독 성공");
//    }

    private record ChatMessage(String username, String content) {
    }
}
