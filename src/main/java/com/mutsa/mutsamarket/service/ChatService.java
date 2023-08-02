package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.ChatMessage;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.chat.ChatRepository;
import com.mutsa.mutsamarket.repository.item.ItemRepository;
import com.mutsa.mutsamarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;;

    @Transactional
    public Long createRoom(Long itemId, String username) {
        Item item = itemRepository.getWithUser(itemId);
        Users buyer = userRepository.getUserByUsername(username);

        Optional<Chat> optionalChatRoom = chatRepository.findByItemAndBuyer(item, buyer);
        if (optionalChatRoom.isPresent()) {
            return optionalChatRoom.get().getId();
        }

        Chat chat = Chat.builder()
                .item(item)
                .seller(item.getUser())
                .buyer(buyer)
                .build();

        return chatRepository.save(chat).getId();
    }

    public Chat findChat(Long chatId, String username) {
        Chat chat = chatRepository.getWithUsers(chatId);
        chat.verifyAccess(username);
        return chat;
    }

    @Transactional
    public void addChatMessage(Long chatId, String username, String content) {
        Chat chat = chatRepository.getChatById(chatId);

        ChatMessage message = ChatMessage.builder()
                .username(username)
                .content(content)
                .build();

        chat.addMessage(message);
    }

    public List<Chat> findChats(String username) {
        Users user = userRepository.getUserByUsername(username);
        return chatRepository.findBySellerOrBuyer(user, user);
    }
}
