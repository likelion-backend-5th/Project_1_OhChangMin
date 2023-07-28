package com.mutsa.mutsamarket.entity;

import com.mutsa.mutsamarket.entity.enumtype.ItemStatus;
import com.mutsa.mutsamarket.exception.AlreadySoldOutException;
import jakarta.persistence.*;
import lombok.*;

import static com.mutsa.mutsamarket.entity.enumtype.ItemStatus.*;
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

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    public void setUser(Users user) {
        this.user = user;
    }

    public void change(String username, Item item) {
        checkUser(username);
        title = item.getTitle();
        description = item.getDescription();
        minPriceWanted = item.getMinPriceWanted();
    }

    public void addImage(String username, String imageUrl) {
        checkUser(username);
        this.imageUrl = imageUrl;
    }

    public void checkUser(String username) {
        this.user.checkEquals(username);
    }

    public void soldOut() {
        if (status == SOLD_OUT) {
            throw new AlreadySoldOutException();
        }
        status = SOLD_OUT;
    }

    public boolean isSeller(String username) {
        return user.equals(username);
    }
}
