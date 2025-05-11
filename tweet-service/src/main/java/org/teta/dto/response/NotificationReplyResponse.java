package org.teta.dto.response;

import org.teta.dto.response.tweet.TweetResponse;
import org.teta.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationReplyResponse {
    private Long tweetId;
    private NotificationType notificationType;
    private TweetResponse tweet;
}
