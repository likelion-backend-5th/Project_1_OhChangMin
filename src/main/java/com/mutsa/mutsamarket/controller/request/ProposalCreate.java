package com.mutsa.mutsamarket.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalCreate {

    @NotNull
    private Integer suggestedPrice;
}
