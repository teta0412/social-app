package org.teta.service;

import dto.response.notification.NotificationUserResponse;
import dto.response.tweet.TweetResponse;
import org.teta.repository.projection.NotificationInfoProjection;
import org.teta.repository.projection.NotificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    Page<NotificationProjection> getUserNotifications(Pageable pageable);

    Page<TweetResponse> getUserMentionsNotifications(Pageable pageable);

    List<NotificationUserResponse> getTweetAuthorsNotifications();

    NotificationInfoProjection getUserNotificationById(Long notificationId);

    Page<TweetResponse> getNotificationsFromTweetAuthors(Pageable pageable);
}
