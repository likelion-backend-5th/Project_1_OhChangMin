package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.item.ItemRepository;
import com.mutsa.mutsamarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void register(Item item, String username) {
        Users user = userRepository.getUserByUsername(username);

        item.setUser(user);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Page<Item> findItems(int page, int limit) {
        return itemRepository.findAllWithUser(PageRequest.of(page - 1, limit));
    }

    @Transactional(readOnly = true)
    public Item findItem(Long itemId) {
        return itemRepository.getWithUser(itemId);
    }

    public void modify(Long itemId, Item item, String username) {
        Item findItem = itemRepository.getWithUser(itemId);

        findItem.change(username, item);
    }

    public void addImage(Long itemId, String username, String imageUrl) {
        Item item = itemRepository.getWithUser(itemId);

        item.addImage(username, imageUrl);
    }

    public void delete(Long itemId, String username) {
        Item findItem = itemRepository.getWithUser(itemId);

        findItem.checkUser(username);
        itemRepository.delete(findItem);
    }
}
