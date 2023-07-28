package com.mutsa.mutsamarket.controller.restcontroller;

import com.mutsa.mutsamarket.controller.request.Login;
import com.mutsa.mutsamarket.controller.request.SignUp;
import com.mutsa.mutsamarket.controller.response.JwtResponse;
import com.mutsa.mutsamarket.controller.response.Response;
import com.mutsa.mutsamarket.service.security.CustomUserDetails;
import com.mutsa.mutsamarket.exception.PasswordMismatchException;
import com.mutsa.mutsamarket.jwt.JwtTokenUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mutsa.mutsamarket.controller.response.ResponseMessageConst.LOGIN_SUCCESS;
import static com.mutsa.mutsamarket.controller.response.ResponseMessageConst.SIGNUP_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/sign-up")
    public Response signUp(@Valid @RequestBody SignUp signUp) {
        userDetailsManager.createUser(
                CustomUserDetails.builder()
                        .username(signUp.getUsername())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .email(signUp.getEmail())
                        .phoneNumber(signUp.getPhoneNumber())
                        .address(signUp.getAddress())
                        .build());

        return new Response(SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody Login login) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(login.getUsername());

        checkMatchedPassword(login.getPassword(), userDetails.getPassword());
        String token = jwtTokenUtils.generateToken(userDetails.getUsername());
        return new JwtResponse(LOGIN_SUCCESS, token);
    }

    private void checkMatchedPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new PasswordMismatchException();
        }
    }
}
