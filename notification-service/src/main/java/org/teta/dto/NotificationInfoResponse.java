package org.teta.dto;

import org.teta.dto.response.tweet.TweetResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationInfoResponse {
    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private UserResponse user;
    private TweetResponse tweet;
}
