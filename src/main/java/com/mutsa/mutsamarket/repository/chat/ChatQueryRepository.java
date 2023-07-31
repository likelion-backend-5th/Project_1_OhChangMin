package com.mutsa.mutsamarket.repository.chat;

import com.mutsa.mutsamarket.entity.Chat;

import java.util.List;

public interface ChatQueryRepository {

    Chat getWithUsers(Long id);
}
