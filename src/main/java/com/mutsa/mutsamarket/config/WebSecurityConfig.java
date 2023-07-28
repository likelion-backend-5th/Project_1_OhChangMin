package com.mutsa.mutsamarket.config;

import com.mutsa.mutsamarket.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


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
                                .requestMatchers(HttpMethod.GET, "/api/items").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/items/{itemId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/items/{itemId}/comments").permitAll()
                                .requestMatchers(
                                        "/api/auth/**",
                                        "/auth/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/")
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
