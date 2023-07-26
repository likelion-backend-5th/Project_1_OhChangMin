package com.mutsa.mutsamarket.security;

import com.mutsa.mutsamarket.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;

    public CustomUserDetails(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails fromEntity(Users users) {
        return CustomUserDetails.builder()
                .id(users.getId())
                .username(users.getUsername())
                .password(users.getPassword())
                .email(users.getEmail())
                .phoneNumber(users.getPhoneNumber())
                .build();
    }

    public Users newEntity() {
        return Users.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

}
