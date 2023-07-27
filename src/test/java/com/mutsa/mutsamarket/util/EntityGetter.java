package com.mutsa.mutsamarket.util;

import com.mutsa.mutsamarket.entity.Comment;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ItemStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EntityGetter {

    public static Item getItem(Users user, String title, String description, int minPriceWanted) {
        return Item.builder()
                .user(user)
                .title(title)
                .description(description)
                .minPriceWanted(minPriceWanted)
                .status(ItemStatus.SALE)
                .build();
    }


    public static Users getUser(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return Users.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }

    public static Comment getComment(Item item, Users user2, String content) {
        return Comment.builder()
                .item(item)
                .user(user2)
                .content(content)
                .build();
    }
}
