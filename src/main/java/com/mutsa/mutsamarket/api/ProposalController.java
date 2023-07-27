package com.mutsa.mutsamarket.api;

import com.mutsa.mutsamarket.api.request.ProposalCreate;
import com.mutsa.mutsamarket.api.request.ProposalProgress;
import com.mutsa.mutsamarket.api.response.ProposalResponse;
import com.mutsa.mutsamarket.api.response.Response;

import com.mutsa.mutsamarket.api.response.ResponseMessageConst;
import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.security.AuthorizedUserGetter;
import com.mutsa.mutsamarket.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static com.mutsa.mutsamarket.api.response.ResponseMessageConst.*;
import static com.mutsa.mutsamarket.entity.enumtype.ProposalStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    @PostMapping
    public Response create(@PathVariable Long itemId,
                           @Valid @RequestBody ProposalCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        proposalService.propose(itemId, username, request.getSuggestedPrice());
        return new Response(PROPOSAL_CREATE);
    }

    @GetMapping
    public Page<ProposalResponse> findNegotiations(@PathVariable Long itemId,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "25") Integer limit) {
        String username = AuthorizedUserGetter.getUsername();
        return proposalService.findProposals(itemId, username, page, limit)
                .map(ProposalResponse::fromEntity);
    }

    @PutMapping("{proposalId}")
    public Response update(@PathVariable Long itemId,
                           @PathVariable Long proposalId,
                           @Valid @RequestBody ProposalCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        proposalService.modify(itemId, proposalId, username, request.getSuggestedPrice());
        return new Response(PROPOSAL_UPDATE);
    }

    @DeleteMapping("{proposalId}")
    public Response delete(@PathVariable Long itemId,
                           @PathVariable Long proposalId) {
        String username = AuthorizedUserGetter.getUsername();
        proposalService.delete(itemId, proposalId, username);
        return new Response(PROPOSAL_DELETE);
    }

    @PutMapping("{proposalId}/progress")
    public Response progress(@PathVariable Long itemId,
                                  @PathVariable Long proposalId,
                                  @Valid @RequestBody ProposalProgress request) {
        String username = AuthorizedUserGetter.getUsername();
        ProposalStatus status = fromValue(request.getStatus());

        switch (status) {
            case ACCEPT, REFUSE ->  {
                proposalService.response(itemId, proposalId, username, status);
                return new Response(PROPOSAL_RESPONSE);
            }
            case CONFIRMED -> {
                proposalService.confirm(itemId, proposalId, username);
                return new Response(PROPOSAL_CONFIRM);
            }
            default -> {
                return new Response(NOT_CORRECT_STATUS);
            }
        }
    }
}
