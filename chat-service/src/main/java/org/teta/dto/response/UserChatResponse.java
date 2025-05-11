package org.teta.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teta.dto.response.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChatResponse extends UserResponse {
    @JsonProperty("isUserChatParticipant")
    private boolean isUserChatParticipant;
}
