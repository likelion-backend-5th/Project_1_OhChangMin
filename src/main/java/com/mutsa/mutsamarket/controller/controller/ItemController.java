package com.mutsa.mutsamarket.controller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        System.out.println("authentication.getName() = " + authentication.getName());
        return "items/list";
    }
}
