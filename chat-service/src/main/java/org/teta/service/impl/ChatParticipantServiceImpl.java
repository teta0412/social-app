package org.teta.service.impl;

import org.teta.exception.ApiRequestException;
import org.teta.constants.ChatErrorMessage;
import org.teta.constants.ChatSuccessMessage;
import org.teta.model.Chat;
import org.teta.model.ChatParticipant;
import org.teta.model.User;
import org.teta.repository.ChatParticipantRepository;
import org.teta.repository.ChatRepository;
import org.teta.repository.projection.UserChatProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.ChatParticipantService;
import org.teta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teta.util.AuthUtil;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserProjection getParticipant(Long participantId, Long chatId) {
        if (!chatRepository.isChatExists(chatId, AuthUtil.getAuthenticatedUserId())) {
            throw new ApiRequestException(ChatErrorMessage.CHAT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        User user = chatParticipantRepository.getChatParticipant(participantId, chatId)
                .orElseThrow(() -> new ApiRequestException(ChatErrorMessage.CHAT_PARTICIPANT_NOT_FOUND, HttpStatus.NOT_FOUND))
                .getUser();
        return userService.getUserProjectionById(user.getId());
    }

    @Override
    @Transactional
    public String leaveFromConversation(Long participantId, Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ApiRequestException(ChatErrorMessage.CHAT_NOT_FOUND, HttpStatus.NOT_FOUND));
        ChatParticipant chatParticipant = chatParticipantRepository.getChatParticipant(participantId, chatId)
                .orElseThrow(() -> new ApiRequestException(ChatErrorMessage.CHAT_PARTICIPANT_NOT_FOUND, HttpStatus.NOT_FOUND));
        chatParticipant.setLeftChat(true);

        if (chat.getParticipants().stream().allMatch(ChatParticipant::isLeftChat)) {
            chatRepository.delete(chat);
            return ChatSuccessMessage.CHAT_SUCCESSFULLY_DELETED;
        }
        return ChatSuccessMessage.SUCCESSFULLY_LEFT_THE_CHAT;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserChatProjection> searchUsersByUsername(String username, Pageable pageable) {
        return userService.searchUsersByUsername(username, pageable);
    }
}
