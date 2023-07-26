package com.mutsa.mutsamarket.entity;

import com.mutsa.mutsamarket.exception.PasswordMismatchException;
import com.mutsa.mutsamarket.exception.UserMismatchedException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Users {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String address;

    public void checkEquals(Users user) {
        if (!id.equals(user.getId())) {
            throw new UserMismatchedException();
        }
    }
}
