package com.mutsa.mutsamarket.api;

import com.mutsa.mutsamarket.api.request.ProposalCreate;
import com.mutsa.mutsamarket.api.response.ProposalResponse;
import com.mutsa.mutsamarket.api.response.Response;

import com.mutsa.mutsamarket.security.AuthorizedUserGetter;
import com.mutsa.mutsamarket.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
        return new Response("구매 제안이 등록되었습니다.");
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
        return new Response("제안이 수정되었습니다.");
    }
}
