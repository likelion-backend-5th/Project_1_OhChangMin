package com.mutsa.mutsamarket.repository.item;

import com.mutsa.mutsamarket.entity.Item;

public interface ItemQueryRepository {

    Item getWithUser(Long id);
}
