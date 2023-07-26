package com.mutsa.mutsamarket.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {

    @NotBlank(message = "username을 입력해야합니다.")
    private String username;

    @NotBlank(message = "password을 입력해야합니다.")
    private String password;

    private String phoneNumber;
    private String email;
    private String address;
}
