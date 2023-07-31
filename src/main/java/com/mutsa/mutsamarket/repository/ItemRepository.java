package com.mutsa.mutsamarket.repository;

import com.mutsa.mutsamarket.entity.Item;
import com.mutsa.mutsamarket.exception.NotFoundItemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join fetch i.user")
    Page<Item> findAllWithUser(PageRequest pageRequest);

    default Item getById(Long id) {
        return findById(id).orElseThrow(NotFoundItemException::new);
    }
}
