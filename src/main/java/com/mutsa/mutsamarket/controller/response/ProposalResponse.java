package com.mutsa.mutsamarket.controller.response;

import com.mutsa.mutsamarket.entity.Proposal;
import lombok.Data;

@Data
public class ProposalResponse {

    private Long id;
    private Integer suggestedPrice;
    private String status;

    public static ProposalResponse fromEntity(Proposal proposal) {
        ProposalResponse proposalResponse = new ProposalResponse();
        proposalResponse.setId(proposal.getId());
        proposalResponse.setSuggestedPrice(proposal.getSuggestedPrice());
        proposalResponse.setStatus(proposal.getStatus().getValue());
        return proposalResponse;
    }
}
