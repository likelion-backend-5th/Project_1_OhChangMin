package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void register(Item item, String username) {
        Users user = userRepository.getByUsername(username);

        item.setUser(user);
        itemRepository.save(item);
    }

    public Page<Item> findItems(int page, int limit) {
        return itemRepository.findAll(PageRequest.of(page - 1, limit));
    }

    public Item findItem(Long itemId) {
        return itemRepository.getById(itemId);
    }

    @Transactional
    public void modify(Long itemId, Item item, String username) {
        Item findItem = itemRepository.getById(itemId);
        Users user = userRepository.getByUsername(username);

        findItem.change(user, item);
    }

    @Transactional
    public void delete(Long itemId, String username) {
        Item findItem = itemRepository.getById(itemId);
        Users user = userRepository.getByUsername(username);

        findItem.checkUser(user);
        itemRepository.delete(findItem);
    }
}
