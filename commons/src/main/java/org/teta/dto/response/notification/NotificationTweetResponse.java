package org.teta.dto.response.notification;

import lombok.Data;

@Data
public class NotificationTweetResponse {
    private Long id;
    private String text;
    private NotificationUserResponse author;
    private boolean notificationCondition;
}
