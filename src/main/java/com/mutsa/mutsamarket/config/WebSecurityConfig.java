package com.mutsa.mutsamarket.config;

import com.mutsa.mutsamarket.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authHttp -> authHttp
                                .requestMatchers(new AntPathRequestMatcher("/api/items", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/items/{itemId}", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/items/{itemId}/comments", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/items", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/items/{itemId}", "GET")).permitAll()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/items")
                                .failureUrl("/auth/login?fail")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/auth/logout")
                                .logoutSuccessUrl("/auth/login")
                )
                .addFilterBefore(jwtTokenFilter, AuthorizationFilter.class);
        return http.build();
    }
}
