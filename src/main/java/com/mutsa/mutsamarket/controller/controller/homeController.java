package com.mutsa.mutsamarket.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping
    public String home() {
        return "redirect:/items";
    }
}
