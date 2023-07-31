package com.mutsa.mutsamarket.repository.user;

import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.exception.NotFoundUserException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    default Users getByUsername(String username) {
        return findByUsername(username).orElseThrow(NotFoundUserException::new);
    };
}
