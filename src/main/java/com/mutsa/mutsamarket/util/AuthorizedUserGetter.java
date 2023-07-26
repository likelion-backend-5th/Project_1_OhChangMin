package com.mutsa.mutsamarket.util;

import com.mutsa.mutsamarket.exception.NotExistAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizedUserGetter {

    public static String getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new NotExistAuthenticationException();
        }
        return username;
    }
}
