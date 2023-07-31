package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.exception.NotAllowConfirmException;
import com.mutsa.mutsamarket.exception.NotAllowResponseException;
import com.mutsa.mutsamarket.repository.item.ItemRepository;
import com.mutsa.mutsamarket.repository.proposal.ProposalRepository;
import com.mutsa.mutsamarket.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static com.mutsa.mutsamarket.entity.enumtype.ItemStatus.SOLD_OUT;
import static com.mutsa.mutsamarket.entity.enumtype.ProposalStatus.*;
import static com.mutsa.mutsamarket.util.EntityGetter.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProposalServiceTest {

    @Autowired UserRepository userRepository;

    @Autowired ItemRepository itemRepository;

    @Autowired ProposalRepository proposalRepository;

    @Autowired ProposalService proposalService;

    @Autowired EntityManager em;

    @Test
    @DisplayName("아이템 주인 제안 페이징 조회 성공")
    void test1() {
        String owner = "owner";
        Item item = setFindProposalTest(owner, "user2", "user3");

        Page<Proposal> proposals = proposalService.findProposals(item.getId(), owner, 1, 25);
        assertThat(proposals.getContent().size()).isEqualTo(25);
    }

    @Test
    @DisplayName("제안자 제안 페이징 조회 성공")
    void test2() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Item item = setFindProposalTest(owner, user1name, user2name);

        Page<Proposal> proposals = proposalService.findProposals(item.getId(), user1name, 1, 25);

        assertThat(proposals.getContent().size()).isEqualTo(15);
    }

    @Test
    @DisplayName("아이템 주인, 제안자가 아닌 유저 페이징 조회 시 0개 조회")
    void test3() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        String whoAreYou = "who are you";
        Users user = getUser(whoAreYou, "1234");
        userRepository.save(user);

        Item item = setFindProposalTest(owner, user1name, user2name);

        Page<Proposal> proposals = proposalService.findProposals(item.getId(), whoAreYou, 1, 25);

        assertThat(proposals.getContent().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("제안 수락, 거절 성공")
    void test4() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Users itemOwner = getUser(owner, "1234");
        Users user1 = getUser(user1name, "1234");
        Users user2 = getUser(user2name, "1234");
        userRepository.save(itemOwner);
        userRepository.save(user1);
        userRepository.save(user2);

        Item item = getItem(itemOwner, "title", "description", 10000);
        itemRepository.save(item);

        Proposal proposal = Proposal.createProposal(item, user1, 12000);
        proposalRepository.save(proposal);

        proposalService.response(proposal.getId(), owner, ACCEPT);

        Proposal findProposal = proposalRepository.findById(proposal.getId()).get();

        assertThat(findProposal.getStatus()).isEqualTo(ACCEPT);
    }

    @Test
    @DisplayName("제안 확정 성공")
    void test5() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Users itemOwner = getUser(owner, "1234");
        Users user1 = getUser(user1name, "1234");
        Users user2 = getUser(user2name, "1234");
        userRepository.save(itemOwner);
        userRepository.save(user1);
        userRepository.save(user2);

        Item item = getItem(itemOwner, "title", "description", 10000);
        itemRepository.save(item);

        Proposal proposal1 = Proposal.createProposal(item, user1, 12000);
        Proposal proposal2 = Proposal.createProposal(item, user2, 13000);
        proposalRepository.save(proposal1);
        proposalRepository.save(proposal2);

        proposalService.response(proposal1.getId(), owner, ACCEPT);
        proposalService.confirm(item.getId(), proposal1.getId(), user1.getUsername());

        em.flush();
        em.clear();

        Proposal findProposal1 = proposalRepository.findById(proposal1.getId()).get();
        Proposal findProposal2 = proposalRepository.findById(proposal2.getId()).get();
        Item findItem = itemRepository.findById(item.getId()).get();

        assertThat(findItem.getStatus()).isEqualTo(SOLD_OUT);
        assertThat(findProposal1.getStatus()).isEqualTo(CONFIRMED);
        assertThat(findProposal2.getStatus()).isEqualTo(REFUSE);
    }

    @Test
    @DisplayName("제안 수락 시 제안 상태가 아닐 경우 예외")
    void test6() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Users itemOwner = getUser(owner, "1234");
        Users user1 = getUser(user1name, "1234");
        Users user2 = getUser(user2name, "1234");
        userRepository.save(itemOwner);
        userRepository.save(user1);
        userRepository.save(user2);

        Item item = getItem(itemOwner, "title", "description", 10000);
        itemRepository.save(item);

        Proposal proposal1 = Proposal.createProposal(item, user1, 12000);
        Proposal proposal2 = Proposal.createProposal(item, user2, 13000);
        proposalRepository.save(proposal1);
        proposalRepository.save(proposal2);

        proposalService.response(proposal1.getId(), owner, ACCEPT);
        proposalService.confirm(item.getId(), proposal1.getId(), user1.getUsername());

        em.flush();
        em.clear();

        assertThatThrownBy(() -> proposalService.response(proposal2.getId(), owner, ACCEPT))
                .isInstanceOf(NotAllowResponseException.class);
    }


    @Test
    @DisplayName("제안 확정 시 수락 상태가 아닐 경우 예외")
    void test7() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Users itemOwner = getUser(owner, "1234");
        Users user1 = getUser(user1name, "1234");
        Users user2 = getUser(user2name, "1234");
        userRepository.save(itemOwner);
        userRepository.save(user1);
        userRepository.save(user2);

        Item item = getItem(itemOwner, "title", "description", 10000);
        itemRepository.save(item);

        Proposal proposal1 = Proposal.createProposal(item, user1, 12000);
        Proposal proposal2 = Proposal.createProposal(item, user2, 13000);
        proposalRepository.save(proposal1);
        proposalRepository.save(proposal2);

        proposalService.response(proposal1.getId(), owner, REFUSE);

        assertThatThrownBy(() -> proposalService.confirm(item.getId(), proposal1.getId(), user1.getUsername()))
                .isInstanceOf(NotAllowConfirmException.class);
    }



    /**
     *     itemOwner, user1, user2 저장, item 1개 저장, user1 제안 15개, user2 제안 15개 저장
     *     return 저장된 아이템
     */
    private Item setFindProposalTest(String owner, String user1name, String user2name) {
        Users itemOwner = getUser(owner, "1234");
        Users user1 = getUser(user1name, "1234");
        Users user2 = getUser(user2name, "1234");
        userRepository.save(itemOwner);
        userRepository.save(user1);
        userRepository.save(user2);

        Item item = getItem(itemOwner, "item", "description", 10000);
        itemRepository.save(item);

        for (int i = 0; i < 30; i++) {
            Proposal proposal;
            if (i % 2 == 0) {
                proposal = getProposal(user1, item, i * 1000);
            } else {
                proposal = getProposal(user2, item, i * 1000);
            }
            proposalRepository.save(proposal);
        }
        return item;
    }
}