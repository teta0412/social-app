package org.teta.rest;

import constants.PathConstants;
import dto.HeaderResponse;
import dto.response.notification.NotificationResponse;
import dto.response.notification.NotificationUserResponse;
import dto.response.tweet.TweetResponse;
import org.teta.dto.NotificationInfoResponse;
import org.teta.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_NOTIFICATION)
public class NotificationController {

    private final NotificationMapper notificationMapper;

    @GetMapping(PathConstants.USER)
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<NotificationResponse> response = notificationMapper.getUserNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.MENTIONS)
    public ResponseEntity<List<TweetResponse>> getUserMentionsNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = notificationMapper.getUserMentionsNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.SUBSCRIBES)
    public ResponseEntity<List<NotificationUserResponse>> getTweetAuthorsNotifications() {
        return ResponseEntity.ok(notificationMapper.getTweetAuthorsNotifications());
    }

    @GetMapping(PathConstants.NOTIFICATION_ID)
    public ResponseEntity<NotificationInfoResponse> getUserNotificationById(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationMapper.getUserNotificationById(notificationId));
    }

    @GetMapping(PathConstants.TIMELINE)
    public ResponseEntity<List<TweetResponse>> getNotificationsFromTweetAuthors(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = notificationMapper.getNotificationsFromTweetAuthors(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }
}
