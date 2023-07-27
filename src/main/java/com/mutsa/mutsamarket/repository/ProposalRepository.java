package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Page<Proposal> findByItem(Item item, Pageable of);

    Page<Proposal> findByItemAndUser(Item item, Users user, Pageable of);
}
