package org.teta.event;

import org.teta.dto.response.tweet.TweetResponse;
import org.teta.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetMentionNotificationEvent {
    private NotificationType notificationType;
    private UserNotificationDto notifiedUser;
    private UserNotificationDto user;
    private TweetNotificationDto tweet;
    private TweetResponse tweetResponse;
}
