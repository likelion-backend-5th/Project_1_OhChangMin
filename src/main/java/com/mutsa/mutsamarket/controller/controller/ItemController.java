package com.mutsa.mutsamarket.controller.controller;

import com.mutsa.mutsamarket.controller.response.ItemResponse;
import com.mutsa.mutsamarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String home(Model model) {
        Page<ItemResponse> items = itemService.findItems(1, 25)
                .map(ItemResponse::fromEntity);

        model.addAttribute("items", items);
        return "items/list";
    }
}
