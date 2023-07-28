package com.mutsa.mutsamarket.controller.response;

import com.mutsa.mutsamarket.entity.Item;
import lombok.Data;

@Data
public class ItemResponse {

    private Long id;
    private String username;
    private String title;
    private String description;
    private int minPriceWanted;
    private String status;

    public static ItemResponse fromEntity(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setUsername(item.getUser().getUsername());
        response.setTitle(item.getTitle());
        response.setDescription(item.getDescription());
        response.setMinPriceWanted(item.getMinPriceWanted());
        response.setStatus(item.getStatus().getValue());
        return  response;
    }
}