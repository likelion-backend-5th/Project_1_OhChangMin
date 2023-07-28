package com.mutsa.mutsamarket.controller.request;

import com.mutsa.mutsamarket.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mutsa.mutsamarket.entity.enumtype.ItemStatus.SALE;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Integer minPriceWanted;

    public static Item toEntity(ItemCreate itemCreate) {
        return Item.builder()
                .title(itemCreate.getTitle())
                .description(itemCreate.getDescription())
                .minPriceWanted(itemCreate.getMinPriceWanted())
                .status(SALE)
                .build();
    }
}
