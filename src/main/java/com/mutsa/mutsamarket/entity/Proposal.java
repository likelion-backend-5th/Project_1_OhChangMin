package com.mutsa.mutsamarket.entity;

import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.exception.StatusChangeNotAllowedException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.mutsa.mutsamarket.entity.enumtype.ProposalStatus.*;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Proposal {

    @Id
    @GeneratedValue
    @Column(name = "proposal_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private int suggestedPrice;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    public static Proposal createProposal(Item item, Users user, int suggestedPrice) {
        return Proposal.builder()
                .item(item)
                .user(user)
                .suggestedPrice(suggestedPrice)
                .status(PROPOSAL).build();
    }

    public void change(Users user, Integer suggestedPrice) {
        checkUser(user);
        this.suggestedPrice = suggestedPrice;
    }

    public void response(Users user, ProposalStatus status) {
        item.checkUser(user);
        if (this.status != PROPOSAL) {
            throw new StatusChangeNotAllowedException();
        }
        this.status = status;
    }

    public void confirm(Users user) {
        checkUser(user);
        status = CONFIRMED;
        item.soldOut();
    }

    public void checkUser(Users user) {
        this.user.checkEquals(user);
    }
}
