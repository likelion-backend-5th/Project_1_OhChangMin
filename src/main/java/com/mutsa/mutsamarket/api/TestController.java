package com.mutsa.mutsamarket.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @PostMapping("/test")
    public void test() {
        System.out.println("TestController.test");
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
