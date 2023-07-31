package com.mutsa.mutsamarket.repository.proposal;

import com.mutsa.mutsamarket.entity.Proposal;

public interface ProposalQueryRepository {

    Proposal getWithItemUser(Long id);

    Proposal getWithUser(Long id);

    Proposal getWithItem(Long id);
}
