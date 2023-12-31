package com.mutsa.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String reply;

    public static Comment createComment(Item item, Users user, String content) {
         return Comment.builder()
                 .item(item)
                 .user(user)
                 .content(content)
                 .build();
    }

    public void change(String username, String content) {
        checkUser(username);
        this.content = content;
    }

    public void addReply(String username, String reply) {
        item.checkUser(username);
        this.reply = reply;
    }

    public void checkUser(String username) {
        this.user.checkEquals(username);
    }
}
