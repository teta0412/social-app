package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.teta.dto.response.UserChatResponse;
import org.teta.repository.projection.UserChatProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.ChatParticipantService;

@Component
@RequiredArgsConstructor
public class ChatParticipantMapper {

    private final BasicMapper basicMapper;
    private final ChatParticipantService chatParticipantService;

    public UserResponse getParticipant(Long participantId, Long chatId) {
        UserProjection participant = chatParticipantService.getParticipant(participantId, chatId);
        return basicMapper.convertToResponse(participant, UserResponse.class);
    }

    public String leaveFromConversation(Long participantId, Long chatId) {
        return chatParticipantService.leaveFromConversation(participantId, chatId);
    }

    public HeaderResponse<UserChatResponse> searchParticipantsByUsername(String username, Pageable pageable) {
        Page<UserChatProjection> participants = chatParticipantService.searchUsersByUsername(username, pageable);
        return basicMapper.getHeaderResponse(participants, UserChatResponse.class);
    }
}
