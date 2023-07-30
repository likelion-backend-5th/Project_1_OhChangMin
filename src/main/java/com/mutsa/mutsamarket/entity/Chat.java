package com.mutsa.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users buyer;

    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public Long getId() {
        return id;
    }
}
