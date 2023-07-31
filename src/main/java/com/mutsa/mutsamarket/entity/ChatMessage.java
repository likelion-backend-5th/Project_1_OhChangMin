package com.mutsa.mutsamarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatMessage {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private String username;

    @Lob
    private String content;

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
