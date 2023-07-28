package com.mutsa.mutsamarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsa.mutsamarket.controller.request.ProposalCreate;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.ProposalRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.mutsa.mutsamarket.controller.response.ResponseMessageConst.*;
import static com.mutsa.mutsamarket.util.EntityGetter.*;
import static com.mutsa.mutsamarket.util.LoginUtil.loginAndGetJwtToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProposalRestControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ItemRepository itemRepository;

    @Autowired UserRepository userRepository;

    @Autowired ProposalRepository proposalRepository;

    @Autowired ObjectMapper objectMapper;


    @Test
    @DisplayName("제안 등록 성공")
    void test1() throws Exception {
        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        Users customer = getUser("customer", password);
        userRepository.save(customer);
        int suggestedPrice = 10000;
        ProposalCreate proposalCreate = new ProposalCreate(suggestedPrice);
        String token = loginAndGetJwtToken(mockMvc, customer.getUsername(), password);

        mockMvc.perform(post("/api/items/"+ item.getId() +"/proposals")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PROPOSAL_CREATE))
                .andDo(print());

        List<Proposal> all = proposalRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        Proposal proposal = all.get(0);
        assertThat(proposal.getSuggestedPrice()).isEqualTo(suggestedPrice);
    }

    @Test
    @DisplayName("제안 수정 성공")
    void test2() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String proposalUsername = "proposalUsername";
        Users user2 = getUser(proposalUsername, password);
        userRepository.save(user2);

        Proposal proposal = Proposal.createProposal(item, user2, 10000);
        proposalRepository.save(proposal);

        int newSuggestedPrice = 20000;
        ProposalCreate proposalCreate = new ProposalCreate(newSuggestedPrice);
        String token = loginAndGetJwtToken(mockMvc, proposalUsername, password);

        mockMvc.perform(put("/api/items/"+ item.getId() +"/proposals/" + proposal.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalCreate)))
                .andExpect(jsonPath("$.message").value(PROPOSAL_UPDATE))
                .andDo(print());

        Proposal findProposal = proposalRepository.findById(proposal.getId()).get();
        assertThat(findProposal.getSuggestedPrice()).isEqualTo(newSuggestedPrice);
    }

    @Test
    @DisplayName("제안 삭제 성공")
    void test3() throws Exception {

        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = getItem(user, "title", "description", 100000);
        userRepository.save(user);
        itemRepository.save(item);

        String proposalUsername = "proposalUsername";
        Users user2 = getUser(proposalUsername, password);
        userRepository.save(user2);

        Proposal proposal = Proposal.createProposal(item, user2, 10000);
        proposalRepository.save(proposal);

        String token = loginAndGetJwtToken(mockMvc, proposalUsername, password);

        mockMvc.perform(delete("/api/items/"+ item.getId() +"/proposals/" + proposal.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(PROPOSAL_DELETE))
                .andDo(print());

        assertThatThrownBy(() -> proposalRepository.findById(proposal.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }


}