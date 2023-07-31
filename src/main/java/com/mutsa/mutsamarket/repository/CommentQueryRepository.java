package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.exception.NotFoundCommentException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mutsa.mutsamarket.entity.QComment.comment;
import static com.mutsa.mutsamarket.entity.QItem.item;
import static com.mutsa.mutsamarket.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory query;

    public Comment getWithItemUser(Long id) {
          return Optional.ofNullable(query.selectFrom(comment)
                        .where(comment.id.eq(id))
                        .leftJoin(comment.item, item).fetchJoin()
                        .leftJoin(comment.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundCommentException::new);
    }

    public Comment getWithUser(Long id) {
          return Optional.ofNullable(query.selectFrom(comment)
                        .where(comment.id.eq(id))
                        .leftJoin(comment.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundCommentException::new);
    }

    public Comment getWithItem(Long id) {
        return Optional.ofNullable(query.selectFrom(comment)
                        .where(comment.id.eq(id))
                        .leftJoin(comment.item, item).fetchJoin()
                        .leftJoin(item.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundCommentException::new);
    }

}
