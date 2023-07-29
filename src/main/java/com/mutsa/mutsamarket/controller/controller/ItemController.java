package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.controller.response.ItemResponse;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String itemList(Model model) {
        Page<ItemResponse> items = itemService.findItems(1, 25)
                .map(ItemResponse::fromEntity);

        model.addAttribute("items", items);
        return "items/list";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item findItem = itemService.findItem(itemId);

        ItemResponse item = ItemResponse.fromEntity(findItem);
        model.addAttribute("item", item);
        return "items/item";
    }
}
