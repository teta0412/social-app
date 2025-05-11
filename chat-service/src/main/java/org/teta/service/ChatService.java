package org.teta.service;

import org.teta.repository.projection.ChatProjection;

import java.util.List;

public interface ChatService {

    ChatProjection getChatById(Long chatId);

    List<ChatProjection> getUserChats();

    ChatProjection createChat(Long userId);
}
