package com.mutsa.mutsamarket.controller.restcontroller;

import com.mutsa.mutsamarket.controller.request.ProposalCreate;
import com.mutsa.mutsamarket.controller.request.ProposalProgress;
import com.mutsa.mutsamarket.controller.response.ProposalResponse;
import com.mutsa.mutsamarket.controller.response.Response;

import com.mutsa.mutsamarket.entity.enumtype.ProposalStatus;
import com.mutsa.mutsamarket.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.mutsa.mutsamarket.controller.response.ResponseMessageConst.*;
import static com.mutsa.mutsamarket.entity.enumtype.ProposalStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items/{itemId}/proposals")
public class ProposalRestController {

    private final ProposalService proposalService;

    @PostMapping
    public Response create(@PathVariable Long itemId,
                           @Valid @RequestBody ProposalCreate request,
                           Authentication auth) {
        proposalService.propose(itemId, auth.getName(), request.getSuggestedPrice());
        return new Response(PROPOSAL_CREATE);
    }

    @GetMapping
    public Page<ProposalResponse> findNegotiations(@PathVariable Long itemId,
                                                   @RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(defaultValue = "25") Integer limit,
                                                   Authentication auth) {
        return proposalService.findProposals(itemId, auth.getName(), page, limit)
                .map(ProposalResponse::fromEntity);
    }

    @PutMapping("{proposalId}")
    public Response update(@PathVariable Long proposalId,
                           @Valid @RequestBody ProposalCreate request,
                           Authentication auth) {
        proposalService.modify(proposalId, auth.getName(), request.getSuggestedPrice());
        return new Response(PROPOSAL_UPDATE);
    }

    @DeleteMapping("{proposalId}")
    public Response delete(@PathVariable Long proposalId,
                           Authentication auth) {
        proposalService.delete(proposalId, auth.getName());
        return new Response(PROPOSAL_DELETE);
    }

    @PutMapping("{proposalId}/progress")
    public Response progress(@PathVariable Long itemId,
                             @PathVariable Long proposalId,
                             @Valid @RequestBody ProposalProgress request,
                             Authentication auth) {
        ProposalStatus status = fromValue(request.getStatus());
        switch (status) {
            case ACCEPT, REFUSE ->  {
                proposalService.response(proposalId, auth.getName(), status);
                return new Response(PROPOSAL_RESPONSE);
            }
            case CONFIRMED -> {
                proposalService.confirm(itemId, proposalId, auth.getName());
                return new Response(PROPOSAL_CONFIRM);
            }
            default -> {
                return new Response(NOT_CORRECT_STATUS);
            }
        }
    }
}
