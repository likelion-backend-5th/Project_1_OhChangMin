package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.controller.request.Login;
import com.mutsa.mutsamarket.controller.request.SignUp;
import com.mutsa.mutsamarket.service.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute Login login) {
        return "auth/login";
    }

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute("signUp") SignUp signUp) {
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute SignUp signUp) {
        userDetailsManager.createUser(
                CustomUserDetails.builder()
                        .username(signUp.getUsername())
                        .password(passwordEncoder.encode(signUp.getPassword()))
                        .email(signUp.getEmail())
                        .phoneNumber(signUp.getPhoneNumber())
                        .address(signUp.getAddress())
                        .build());

        return "redirect:/auth/login";
    }
}
