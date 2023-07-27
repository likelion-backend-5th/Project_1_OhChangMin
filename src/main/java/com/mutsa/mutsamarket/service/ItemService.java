package com.mutsa.mutsamarket.service;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.entity.Users;
import com.mutsa.mutsamarket.repository.ItemQueryRepository;
import com.mutsa.mutsamarket.repository.ItemRepository;
import com.mutsa.mutsamarket.repository.UserRepository;
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
    private final ItemQueryRepository itemQueryRepository;
    private final UserRepository userRepository;

    public void register(Item item, String username) {
        Users user = userRepository.getByUsername(username);

        item.setUser(user);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Page<Item> findItems(int page, int limit) {
        return itemRepository.findAll(PageRequest.of(page - 1, limit));
    }

    @Transactional(readOnly = true)
    public Item findItem(Long itemId) {
        return itemRepository.getById(itemId);
    }

    public void modify(Long itemId, Item item, String username) {
        Item findItem = itemQueryRepository.findWithUser(itemId);
        Users user = userRepository.getByUsername(username);

        findItem.change(user, item);
    }

    public void addImage(Long itemId, String username, String imageUrl) {
        Item item = itemQueryRepository.findWithUser(itemId);
        Users user = userRepository.getByUsername(username);

        item.addImage(user, imageUrl);
    }

    public void delete(Long itemId, String username) {
        Item findItem = itemQueryRepository.findWithUser(itemId);
        Users user = userRepository.getByUsername(username);

        findItem.checkUser(user);
        itemRepository.delete(findItem);
    }
}
