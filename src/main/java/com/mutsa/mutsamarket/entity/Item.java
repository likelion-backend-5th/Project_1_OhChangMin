package com.mutsa.mutsamarket.entity;

import com.mutsa.mutsamarket.entity.enumtype.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sales_item")
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Users user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private int minPriceWanted;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    public void setUser(Users user) {
        this.user = user;
    }

    public void change(Users user, Item item) {
        checkUser(user);
        title = item.getTitle();
        description = item.getDescription();
        minPriceWanted = item.getMinPriceWanted();
    }

    public void checkUser(Users user) {
        this.user.checkEquals(user);
    }
}
