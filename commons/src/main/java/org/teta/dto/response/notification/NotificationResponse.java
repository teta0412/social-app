package org.teta.dto.response.notification;

import lombok.Data;
import org.teta.enums.NotificationType;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private NotificationUserResponse notifiedUser;
    private NotificationUserResponse user;
    private NotificationUserResponse userToFollow;
    private NotificationTweetResponse tweet;
    private NotificationListResponse list;
    private boolean isAddedToList;
}
