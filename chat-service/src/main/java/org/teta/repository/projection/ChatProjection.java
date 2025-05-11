package org.teta.repository.projection;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatProjection {
    Long getId();
    LocalDateTime getCreatedAt();
    List<ChatParticipantProjection> getParticipants();

    interface ChatParticipantProjection {
        ChatUserParticipantProjection getUser();
        boolean getLeftChat();
    }
}
