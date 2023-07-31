package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.ChatMessage;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.ChatMessageRepository;
import com.mutsa.mutsamarket.repository.ChatRepository;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public Long createRoom(Long itemId, String username) {
        Item item = itemRepository.getById(itemId);
        Users buyer = userRepository.getByUsername(username);

        Optional<Chat> optionalChatRoom = chatRepository.findByItemAndBuyer(item, buyer);
        if (optionalChatRoom.isPresent()) {
            return optionalChatRoom.get().getId();
        }

        Chat chat = Chat.builder()
                .item(item)
                .buyer(buyer)
                .build();

        return chatRepository.save(chat).getId();
    }

    public Chat findChat(Long itemId, Long chatRoomId) {
        Item item = itemRepository.getById(itemId);

        //TODO 예외 변경, 패치 조인으로 쿼리 변경
        return chatRepository.findByIdAndItem(chatRoomId, item)
                .orElseThrow(() -> new IllegalArgumentException("채팅을 찾을 수 없습니다."));
    }

    //TODO 채팅 추가 추후 리펙토링
    @Transactional
    public void addChatMessage(Long chatId, String username, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅을 찾을 수 없습니다."));

        ChatMessage message = ChatMessage.builder()
                .username(username)
                .content(content)
                .build();

        chat.addMessage(message);
    }
}
