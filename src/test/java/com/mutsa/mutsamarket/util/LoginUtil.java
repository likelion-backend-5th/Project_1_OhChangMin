package com.mutsa.mutsamarket.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutsa.mutsamarket.api.request.Login;
import com.mutsa.mutsamarket.api.response.JwtResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class LoginUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String loginAndGetJwtToken(MockMvc mockMvc, String username, String password) throws Exception {
        Login login = new Login(username, password);
        String loginRequestJson = objectMapper.writeValueAsString(login);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(responseBody, JwtResponse.class);
        return jwtResponse.getToken();
    }
}
