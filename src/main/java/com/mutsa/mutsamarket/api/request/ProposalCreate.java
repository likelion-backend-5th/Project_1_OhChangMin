package com.mutsa.mutsamarket.api.request;

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
