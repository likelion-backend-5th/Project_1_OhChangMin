package com.mutsa.mutsamarket.repository.chat;

import com.mutsa.mutsamarket.entity.Chat;
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
        return Optional.ofNullable(query.selectFrom(chat)
                        .where(chat.id.eq(id))
                        .leftJoin(chat.seller, users).fetchJoin()
                        .leftJoin(chat.buyer, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundChatException::new);
    }
}
