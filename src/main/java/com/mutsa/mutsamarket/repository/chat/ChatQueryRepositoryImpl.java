package com.mutsa.mutsamarket.repository.chat;

import com.mutsa.mutsamarket.entity.Chat;
import com.mutsa.mutsamarket.entity.QUsers;
import com.mutsa.mutsamarket.exception.NotFoundChatException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mutsa.mutsamarket.entity.QChat.*;
import static com.mutsa.mutsamarket.entity.QUsers.*;

@Repository
@RequiredArgsConstructor
public class ChatQueryRepositoryImpl implements ChatQueryRepository {

    private final JPAQueryFactory query;

    public Chat getWithUsers(Long id) {
        QUsers seller = new QUsers("seller");
        QUsers buyer = new QUsers("buyer");

        return Optional.ofNullable(
                        query.selectFrom(chat)
                                .leftJoin(chat.seller, seller).fetchJoin()
                                .leftJoin(chat.buyer, buyer).fetchJoin()
                                .where(chat.id.eq(id))
                                .fetchOne())
                .orElseThrow(NotFoundChatException::new);
    }
}
