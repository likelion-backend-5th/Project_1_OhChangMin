package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.exception.NotFoundCommentException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mutsa.mutsamarket.entity.QItem.item;
import static com.mutsa.mutsamarket.entity.QProposal.*;
import static com.mutsa.mutsamarket.entity.QUsers.users;

@Repository
@RequiredArgsConstructor
public class ProposalQueryRepository {

    private final JPAQueryFactory query;

    public Proposal findWithItemUser(Long id) {
          return Optional.ofNullable(query.selectFrom(proposal)
                        .where(proposal.id.eq(id))
                        .leftJoin(proposal.item, item).fetchJoin()
                        .leftJoin(proposal.user, users).fetchJoin()
                        .fetchOne())
                .orElseThrow(NotFoundCommentException::new);
    }
}