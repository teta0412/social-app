package org.teta.service;

import org.teta.repository.projection.UserChatProjection;
import org.teta.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatParticipantService {

    UserProjection getParticipant(Long participantId, Long chatId);

    String leaveFromConversation(Long participantId, Long chatId);

    Page<UserChatProjection> searchUsersByUsername(String username, Pageable pageable);
}
