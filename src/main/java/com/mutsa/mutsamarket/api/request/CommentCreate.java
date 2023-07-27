package com.mutsa.mutsamarket.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreate {

    @NotBlank
    private String content;
}
