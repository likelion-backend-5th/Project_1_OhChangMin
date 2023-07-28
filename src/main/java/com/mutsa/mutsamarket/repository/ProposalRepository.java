package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Page<Proposal> findByItem(Item item, Pageable of);

    @Query("select p from Proposal p where p.item = :item and p.user.username = :username")
    Page<Proposal> findByItemAndUsername(@Param("item") Item item, @Param("username") String username, Pageable of);

    @Modifying
    @Query("update Proposal p set p.status = :status where p.id != :proposalId and p.item.id = :itemId")
    void updateStatusByIdNot(@Param("itemId") Long itemId,
                             @Param("proposalId") Long proposalId,
                             @Param("status") ProposalStatus status);
}
