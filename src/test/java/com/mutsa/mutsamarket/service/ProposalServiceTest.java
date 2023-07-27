package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.exception.NotFoundProposalException;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.ProposalRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import com.mutsa.mutsamarket.util.EntityGetter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static com.mutsa.mutsamarket.util.EntityGetter.*;
import static com.mutsa.mutsamarket.util.EntityGetter.getItem;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProposalServiceTest {

    @Autowired UserRepository userRepository;

    @Autowired ItemRepository itemRepository;

    @Autowired ProposalRepository proposalRepository;

    @Autowired ProposalService proposalService;

    @Test
    @DisplayName("아이템 주인 제안 페이징 조회 성공")
    void test1() {
        String owner = "owner";
        Item item = setProposals(owner, "user2", "user3");

        Page<Proposal> proposals = proposalService.findProposals(item.getId(), owner, 1, 25);
        assertThat(proposals.getContent().size()).isEqualTo(25);
    }

    @Test
    @DisplayName("제안자 제안 페이징 조회 성공")
    void test2() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        Item item = setProposals(owner, user1name, user2name);

        Page<Proposal> proposals = proposalService.findProposals(item.getId(), user1name, 1, 25);

        assertThat(proposals.getContent().size()).isEqualTo(15);
    }

    @Test
    @DisplayName("아이템 주인, 제안자가 아닌 유저 페이징 조회 시 예외발생")
    void test3() {
        String owner = "owner";
        String user1name = "user1";
        String user2name = "user2";

        String whoAreYou = "who are you";
        Users user = getUser(whoAreYou, "1234");
        userRepository.save(user);

        Item item = setProposals(owner, user1name, user2name);

        assertThatThrownBy(() -> proposalService.findProposals(item.getId(), whoAreYou, 1, 25))
                .isInstanceOf(NotFoundProposalException.class);
    }

    private Item setProposals(String owner, String user1name, String user2name) {
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