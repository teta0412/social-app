package org.teta.dto.response;

import dto.response.tweet.TweetResponse;
import dto.response.user.UserResponse;
import enums.NotificationType;
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
