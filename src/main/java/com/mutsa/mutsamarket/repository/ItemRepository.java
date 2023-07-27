package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.exception.NotFoundItemException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    default Item getById(Long id) {
        return findById(id).orElseThrow(NotFoundItemException::new);
    };
}
