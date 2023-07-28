package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.exception.NotFoundItemException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mutsa.mutsamarket.entity.QItem.*;
import static com.mutsa.mutsamarket.entity.QUsers.*;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {

    private final JPAQueryFactory query;

    public Item getWithUser(Long id) {
        return Optional.ofNullable(
                query.selectFrom(item)
                        .where(item.id.eq(id))
                        .leftJoin(item.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundItemException::new);
    }
}
