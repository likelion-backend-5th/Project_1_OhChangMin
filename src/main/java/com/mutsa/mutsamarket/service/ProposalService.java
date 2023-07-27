package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.exception.NotFoundProposalException;
import com.mutsa.mutsamarket.exception.UserMismatchedException;
import com.mutsa.mutsamarket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final UserRepository userRepository;
    private final ProposalRepository proposalRepository;
    private final ProposalQueryRepository proposalQueryRepository;

    public void propose(Long itemId, String username, Integer suggestedPrice) {
        Item item = itemRepository.getById(itemId);
        Users user = userRepository.getByUsername(username);

        Proposal proposal = Proposal.createProposal(item, user, suggestedPrice);
        proposalRepository.save(proposal);
    }

    @Transactional(readOnly = true)
    public Page<Proposal> findProposals(Long itemId, String username, Integer page, Integer limit) {
        Item item = itemQueryRepository.findWithUser(itemId);
        Users user = userRepository.getByUsername(username);
        PageRequest pageRequest = PageRequest.of(page - 1, limit);

        try {
            item.checkUser(user);
            return proposalRepository.findByItem(item, pageRequest);
        } catch (UserMismatchedException e) {
            Page<Proposal> result = proposalRepository.findByItemAndUser(item, user, pageRequest);
            if (!result.hasContent()) {
                throw new NotFoundProposalException(e);
            }
            return result;
        }
    }

    public void modify(Long itemId, Long proposalId, String username, Integer suggestedPrice) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.findWithItemUser(proposalId, item);
        Users user = userRepository.getByUsername(username);

        proposal.change(user, suggestedPrice);
    }

    public void delete(Long itemId, Long proposalId, String username) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.findWithItemUser(proposalId, item);
        Users user = userRepository.getByUsername(username);

        proposal.checkUser(user);
        proposalRepository.delete(proposal);
    }

    public void response(Long itemId, Long proposalId, String username, ProposalStatus status) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.findWithItemUser(proposalId, item);
        Users user = userRepository.getByUsername(username);

        proposal.response(user, status);
    }

    public void confirm(Long itemId, Long proposalId, String username) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.findWithItemUser(proposalId, item);
        Users user = userRepository.getByUsername(username);

        proposal.confirm(user);
        proposalRepository.updateStatusByIdNot(itemId, proposalId, ProposalStatus.REFUSE);
    }
}
