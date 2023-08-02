package com.mutsa.mutsamarket;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.item.ItemRepository;
import com.mutsa.mutsamarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.mutsa.mutsamarket.entity.enumtype.ItemStatus.SALE;

@Profile("web-dev")
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        Users kim = Users.builder()
                .username("kim")
                .password(passwordEncoder.encode("1234"))
                .build();

        Users park = Users.builder()
                .username("park")
                .password(passwordEncoder.encode("1234"))
                .build();

        userRepository.save(kim);
        userRepository.save(park);

        Item item1 = Item.builder().title("item1")
                .description("description1")
                .user(park).minPriceWanted(1000)
                .status(SALE)
                .build();

        Item item2 = Item.builder().title("item2")
                .description("description2")
                .user(park).minPriceWanted(2000)
                .status(SALE)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);
    }
}
