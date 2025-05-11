package org.teta.controller;

import constants.PathConstants;
import org.teta.dto.response.ChatResponse;
import org.teta.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_CHAT)
public class ChatController {

    private final ChatMapper chatMapper;

    @GetMapping(PathConstants.CHAT_ID)
    public ResponseEntity<ChatResponse> getChatById(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMapper.getChatById(chatId));
    }

    @GetMapping(PathConstants.USERS)
    public ResponseEntity<List<ChatResponse>> getUserChats() {
        return ResponseEntity.ok(chatMapper.getUserChats());
    }

    @GetMapping(PathConstants.CREATE_USER_ID)
    public ResponseEntity<ChatResponse> createChat(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(chatMapper.createChat(userId));
    }
}
