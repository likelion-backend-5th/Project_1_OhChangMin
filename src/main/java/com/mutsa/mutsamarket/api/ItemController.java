package com.mutsa.mutsamarket.api;

import com.mutsa.mutsamarket.api.request.ItemCreate;
import com.mutsa.mutsamarket.api.response.ItemResponse;
import com.mutsa.mutsamarket.api.response.Response;
import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.file.FileStore;
import com.mutsa.mutsamarket.service.ItemService;
import com.mutsa.mutsamarket.security.AuthorizedUserGetter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.mutsa.mutsamarket.api.response.ResponseMessageConst.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;

    @PostMapping
    public Response create(@Valid @RequestBody ItemCreate request) {
        String username = AuthorizedUserGetter.getUsername();
        Item item = ItemCreate.toEntity(request);
        itemService.register(item, username);
        return new Response(ITEM_CREATE);
    }

    @GetMapping
    public Page<ItemResponse> page(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "5") Integer limit) {
        return itemService.findItems(page, limit)
                .map(ItemResponse::fromEntity);
    }

    @GetMapping("{itemId}")
    public ItemResponse find(@PathVariable Long itemId) {
        Item item = itemService.findItem(itemId);
        return ItemResponse.fromEntity(item);
    }

    @PutMapping("{itemId}")
    public Response update(@PathVariable Long itemId,
                           @Valid @RequestBody ItemCreate request) {
        Item item = ItemCreate.toEntity(request);
        String username = AuthorizedUserGetter.getUsername();
        itemService.modify(itemId, item, username);
        return new Response(ITEM_UPDATE);
    }

    @PutMapping("{itemId}/image")
    public Response uploadImage(@PathVariable Long itemId,
                                @RequestPart MultipartFile image) {
        String username = AuthorizedUserGetter.getUsername();
        String imageUrl = fileStore.storeFile(image);
        itemService.addImage(itemId, username, imageUrl);
        return new Response(ADD_IMAGE);
    }

    @DeleteMapping("{itemId}")
    public Response delete(@PathVariable Long itemId) {
        String username = AuthorizedUserGetter.getUsername();
        itemService.delete(itemId, username);
        return new Response(ITEM_DELETE);
    }
}
