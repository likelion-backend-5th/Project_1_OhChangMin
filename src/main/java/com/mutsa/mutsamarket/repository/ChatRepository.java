package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByItemAndBuyer(Item item, Users buyer);

    List<Chat> findBySellerOrBuyer(Users seller, Users buyer);
}
