package event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequestUserEvent implements UserEvent {
    private Long id;
    private String fullName;
    private String username;
    private String about;
    private String avatar;
    private boolean privateProfile;
    private boolean mutedDirectMessages;
    private boolean active;
    private boolean userFollowRequest;
}
