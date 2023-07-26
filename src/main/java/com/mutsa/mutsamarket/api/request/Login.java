package com.mutsa.mutsamarket.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @NotBlank(message = "username을 입력해야합니다.")
    private String username;

    @NotBlank(message = "password을 입력해야합니다.")
    private String password;
}
