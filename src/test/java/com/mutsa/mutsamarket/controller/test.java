package com.mutsa.mutsamarket.controller;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.entity.enumtype.ItemStatus;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mutsa.mutsamarket.util.EntityGetter.getUser;

@Transactional
@SpringBootTest
public class test {

    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepository;

    @Test
    void test1() {
        //given
        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = Item.builder()
                .user(user)
                .title("title")
                .description("description")
                .minPriceWanted(10000)
                .status(ItemStatus.SALE)
                .build();
        userRepository.save(user);
        itemRepository.save(item);
        //when

        System.out.println("item.getId() = " + item.getId());
        //then
    }

    @Test
    void test2() {
        //given
        String username = "lou0124";
        String password = "1234";
        Users user = getUser(username, password);
        Item item = Item.builder()
                .user(user)
                .title("title")
                .description("description")
                .minPriceWanted(10000)
                .status(ItemStatus.SALE)
                .build();
        userRepository.save(user);
        itemRepository.save(item);
        //when
        System.out.println("item.getId() = " + item.getId());

        //then
    }
}
