package com.mutsa.mutsamarket.repository.chat;

import com.mutsa.mutsamarket.entity.Chat;

public interface ChatQueryRepository {

    Chat getWithUsers(Long id);
}
