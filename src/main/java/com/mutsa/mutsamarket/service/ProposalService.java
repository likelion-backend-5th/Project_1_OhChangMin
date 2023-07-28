package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Proposal;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.exception.NotAllowItemSellerProposalException;
import com.mutsa.mutsamarket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mutsa.mutsamarket.entity.Proposal.*;

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
        Item item = itemQueryRepository.getWithUser(itemId);
        if (item.isSeller(username)) {
            throw new NotAllowItemSellerProposalException();
        }
        Users user = userRepository.getByUsername(username);

        proposalRepository.save(createProposal(item, user, suggestedPrice));
    }

    @Transactional(readOnly = true)
    public Page<Proposal> findProposals(Long itemId, String username, Integer page, Integer limit) {
        Item item = itemQueryRepository.getWithUser(itemId);
        PageRequest pageRequest = PageRequest.of(page - 1, limit);

        if (item.isSeller(username)) {
            return proposalRepository.findByItem(item, pageRequest);
        }
        return proposalRepository.findByItemAndUsername(item, username, pageRequest);
    }

    public void modify(Long itemId, Long proposalId, String username, Integer suggestedPrice) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.getWithItemUser(proposalId, item);

        proposal.change(username, suggestedPrice);
    }

    public void delete(Long itemId, Long proposalId, String username) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.getWithItemUser(proposalId, item);

        proposal.checkDeletable(username);
        proposalRepository.delete(proposal);
    }

    public void response(Long itemId, Long proposalId, String username, ProposalStatus status) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.getWithItemUser(proposalId, item);

        proposal.response(username, status);
    }

    public void confirm(Long itemId, Long proposalId, String username) {
        Item item = itemRepository.getById(itemId);
        Proposal proposal = proposalQueryRepository.getWithItemUser(proposalId, item);

        proposal.confirm(username);
        proposalRepository.updateStatusByIdNot(itemId, proposalId, ProposalStatus.REFUSE);
    }
}
