package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.CustomUserDetails;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.exception.NotFoundUserException;
import com.mutsa.mutsamarket.exception.UsernameAlreadyExistsException;
import com.mutsa.mutsamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(NotFoundUserException::new);
        return CustomUserDetails.fromEntity(users);
    }

    @Override
    public void createUser(UserDetails user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        userRepository.save(((CustomUserDetails) user).newEntity());
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
