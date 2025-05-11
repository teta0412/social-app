package org.teta.repository.projection;

import dto.response.tweet.TweetResponse;
import dto.response.user.UserResponse;
import enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();

    @Value("#{target.user == null ? null : @notificationServiceHelper.getUserById(target.user.id)}")
    UserResponse getUser();

    @Value("#{target.tweet == null ? null : @notificationServiceHelper.getTweetById(target.tweet.id)}")
    TweetResponse getTweet();
}
