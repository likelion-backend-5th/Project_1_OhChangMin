package com.mutsa.mutsamarket.jwt;

import com.mutsa.mutsamarket.service.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isJwtAuthorization(authHeader)) {
            String token = getToken(authHeader);
            String username = jwtTokenUtils.getSubject(token);
            setAuthentication(token, username);

            log.info("SecurityContext에 {}가 등록 되었습니다.", username);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isJwtAuthorization(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private String getToken(String authHeader) {
        return authHeader.split(" ")[1];
    }

    private void setAuthentication(String token, String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AbstractAuthenticationToken authenticationToken = createAuthenticationToken(token, username);
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }

    private AbstractAuthenticationToken createAuthenticationToken(String token, String username) {
        return new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(username), token, new ArrayList<>());
    }
}
