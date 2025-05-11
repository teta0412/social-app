package org.teta.controller;

import org.teta.client.WebSocketClient;
import constants.PathConstants;
import constants.WebsocketConstants;
import org.teta.dto.request.ChatMessageRequest;
import org.teta.dto.request.MessageWithTweetRequest;
import org.teta.dto.response.ChatMessageResponse;
import org.teta.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_CHAT)
public class ChatMessageController {

    private final ChatMessageMapper chatMessageMapper;
    private final WebSocketClient webSocketClient;

    @GetMapping(PathConstants.CHAT_ID_MESSAGES)
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMessageMapper.getChatMessages(chatId));
    }

    @GetMapping(PathConstants.CHAT_ID_READ_MESSAGES)
    public ResponseEntity<Long> readChatMessages(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMessageMapper.readChatMessages(chatId));
    }

    @PostMapping(PathConstants.ADD_MESSAGE)
    public ResponseEntity<Void> addMessage(@RequestBody ChatMessageRequest request) {
        chatMessageMapper.addMessage(request)
                .forEach((userId, message) -> webSocketClient.send(WebsocketConstants.TOPIC_CHAT + userId, message));
        return ResponseEntity.ok().build();
    }

    @PostMapping(PathConstants.ADD_MESSAGE_TWEET)
    public ResponseEntity<Void> addMessageWithTweet(@RequestBody MessageWithTweetRequest request) {
        chatMessageMapper.addMessageWithTweet(request)
                .forEach((userId, message) -> webSocketClient.send(WebsocketConstants.TOPIC_CHAT + userId, message));
        return ResponseEntity.ok().build();
    }
}
