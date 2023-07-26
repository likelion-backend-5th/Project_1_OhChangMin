package com.mutsa.mutsamarket.api.response;

import lombok.Data;

@Data
public class JwtResponse {

    private final String message;
    private final String token;
}
