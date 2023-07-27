package com.mutsa.mutsamarket.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalProgress {

    @NotBlank
    @Pattern(regexp = "^(수락|거절|확정)$")
    private String status;
}
