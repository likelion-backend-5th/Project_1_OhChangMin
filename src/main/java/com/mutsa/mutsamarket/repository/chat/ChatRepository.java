package com.mutsa.mutsamarket.repository.chat;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.exception.NotFoundChatException;
import com.mutsa.mutsamarket.exception.NotFoundItemException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>, ChatQueryRepository {

    Optional<Chat> findByItemAndBuyer(Item item, Users buyer);

    List<Chat> findBySellerOrBuyer(Users seller, Users buyer);

    default Chat getById(Long id) {
        return findById(id).orElseThrow(NotFoundChatException::new);
    }
}
