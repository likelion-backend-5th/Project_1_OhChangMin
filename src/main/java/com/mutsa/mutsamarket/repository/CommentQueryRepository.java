package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.exception.NotFoundCommentException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mutsa.mutsamarket.entity.QComment.*;
import static com.mutsa.mutsamarket.entity.QItem.*;
import static com.mutsa.mutsamarket.entity.QUsers.*;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory query;

    public Comment findWithItemUser(Long id, Item paramItem) {
          return Optional.ofNullable(query.selectFrom(comment)
                        .where(comment.id.eq(id).and(comment.item.eq(paramItem)))
                        .leftJoin(comment.item, item).fetchJoin()
                        .leftJoin(comment.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundCommentException::new);
    }
}
