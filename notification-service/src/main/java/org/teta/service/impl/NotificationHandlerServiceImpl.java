package org.teta.service.impl;

import org.teta.broker.producer.UserNotificationProducer;
import org.teta.client.WebSocketClient;
import org.teta.constants.WebsocketConstants;
import org.teta.dto.response.notification.NotificationResponse;
import org.teta.enums.NotificationType;

import org.teta.event.*;
import org.teta.mapper.NotificationHandlerMapper;
import org.teta.model.Lists;
import org.teta.model.Notification;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.NotificationRepository;
import org.teta.service.ListsHandlerService;
import org.teta.service.NotificationHandlerService;
import org.teta.service.TweetHandlerService;
import org.teta.service.UserHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationHandlerServiceImpl implements NotificationHandlerService {

    private final NotificationRepository notificationRepository;
    private final UserNotificationProducer userNotificationProducer;
    private final UserHandlerService userHandlerService;
    private final ListsHandlerService listsHandlerService;
    private final TweetHandlerService tweetHandlerService;
    private final WebSocketClient webSocketClient;
    private final NotificationHandlerMapper notificationHandlerMapper;

    @Override
    @Transactional
    public void handleListsNotification(ListsNotificationEvent event) {
        Long notifiedUserId = event.getNotifiedUser().getId();
        Long authUserId = event.getUser().getId();
        Long listId = event.getLists().getId();

        if (!notifiedUserId.equals(authUserId)) {
            if (!notificationRepository.isNotificationExists(notifiedUserId, authUserId, NotificationType.LISTS, listId)) {
                User notifiedUser = userHandlerService.getOrCreateUser(event.getNotifiedUser());
                User user = userHandlerService.getOrCreateUser(event.getUser());
                Lists list = listsHandlerService.getOrCreateList(event.getLists());
                Notification notification = new Notification();
                notification.setNotificationType(NotificationType.LISTS);
                notification.setNotifiedUser(notifiedUser);
                notification.setUser(user);
                notification.setList(list);
                notificationRepository.save(notification);
                userNotificationProducer.increaseNotificationsCount(notification.getNotifiedUser().getId());
                NotificationResponse response = notificationHandlerMapper.convertToNotificationListResponse(
                        notification, event.isNotificationCondition());
                webSocketClient.send(WebsocketConstants.TOPIC_NOTIFICATIONS + response.getNotifiedUser().getId(), response);
            }
        }
    }

    @Override
    @Transactional
    public void handleFollowUserNotification(FollowUserNotificationEvent event) {
        Long authUserId = event.getUser().getId();
        Long notifiedUserId = event.getNotifiedUser().getId();
        Long followId = event.getUserToFollow().getId();

        if (!notifiedUserId.equals(authUserId)) {
            if (!notificationRepository.isNotificationExists(notifiedUserId, authUserId, NotificationType.FOLLOW, followId)) {
                User notifiedUser = userHandlerService.getOrCreateUser(event.getNotifiedUser());
                User user = userHandlerService.getOrCreateUser(event.getUser());
                User follower = userHandlerService.getOrCreateUser(event.getUserToFollow());
                Notification notification = new Notification();
                notification.setNotificationType(NotificationType.FOLLOW);
                notification.setNotifiedUser(notifiedUser);
                notification.setUser(user);
                notification.setUserToFollow(follower);
                notificationRepository.save(notification);
                userNotificationProducer.increaseNotificationsCount(notification.getNotifiedUser().getId());
                NotificationResponse response = notificationHandlerMapper.convertToNotificationUserResponse(
                        notification, event.isNotificationCondition());
                webSocketClient.send(WebsocketConstants.TOPIC_NOTIFICATIONS + response.getNotifiedUser().getId(), response);
            }
        }
    }

    @Override
    @Transactional
    public void handleTweetNotification(TweetNotificationEvent event) {
        Long authUserId = event.getUser().getId();
        Long notifiedUserId = event.getNotifiedUser().getId();
        Long tweetId = event.getTweet().getId();
        NotificationType notificationType = event.getNotificationType();

        if (!notifiedUserId.equals(authUserId)) {
            if (!notificationRepository.isNotificationExists(notifiedUserId, authUserId, notificationType, tweetId)) {
                User notifiedUser = userHandlerService.getOrCreateUser(event.getNotifiedUser());
                User user = userHandlerService.getOrCreateUser(event.getUser());
                Tweet tweet = tweetHandlerService.getOrCreateTweet(event.getTweet());
                Notification notification = new Notification();
                notification.setNotificationType(event.getNotificationType());
                notification.setNotifiedUser(notifiedUser);
                notification.setUser(user);
                notification.setTweet(tweet);
                notificationRepository.save(notification);
                userNotificationProducer.increaseNotificationsCount(notification.getNotifiedUser().getId());
                NotificationResponse response = notificationHandlerMapper.convertToNotificationTweetResponse(
                        notification, event.isNotificationCondition());
                webSocketClient.send(WebsocketConstants.TOPIC_FEED, response);
                webSocketClient.send(WebsocketConstants.TOPIC_USER_UPDATE_TWEET, response);
                webSocketClient.send(WebsocketConstants.TOPIC_TWEET + response.getTweet().getId(), response);
                webSocketClient.send(WebsocketConstants.TOPIC_NOTIFICATIONS + response.getNotifiedUser().getId(), response);
                return;
            }
        }
        NotificationResponse response = notificationHandlerMapper.convertToNotificationTweetResponse(event);
        webSocketClient.send(WebsocketConstants.TOPIC_FEED, response);
        webSocketClient.send(WebsocketConstants.TOPIC_USER_UPDATE_TWEET, response);
        webSocketClient.send(WebsocketConstants.TOPIC_TWEET + response.getTweet().getId(), response);
    }

    @Override
    @Transactional
    public void handleTweetSubscriberNotification(TweetSubscriberNotificationEvent notificationEvent) {
        Tweet tweet = tweetHandlerService.getOrCreateTweet(notificationEvent.getTweet());
        User user = userHandlerService.getOrCreateUser(notificationEvent.getUser());
        notificationEvent.getSubscribers().stream()
                .map(userHandlerService::getOrCreateUser)
                .toList()
                .forEach(subscriber -> {
                    Notification notification = new Notification();
                    notification.setNotificationType(NotificationType.TWEET);
                    notification.setUser(user);
                    notification.setTweet(tweet);
                    notification.setNotifiedUser(subscriber);
                    notificationRepository.save(notification);
                    userNotificationProducer.increaseNotificationsCount(notification.getNotifiedUser().getId());
                });
    }

    @Override
    @Transactional
    public void handleTweetMentionNotification(TweetMentionNotificationEvent notificationEvent) {
        User notifiedUser = userHandlerService.getOrCreateUser(notificationEvent.getNotifiedUser());
        User user = userHandlerService.getOrCreateUser(notificationEvent.getUser());
        Tweet tweet = tweetHandlerService.getOrCreateTweet(notificationEvent.getTweet());
        Notification notification = new Notification();
        notification.setNotificationType(notificationEvent.getNotificationType());
        notification.setNotifiedUser(notifiedUser);
        notification.setUser(user);
        notification.setTweet(tweet);
        notificationRepository.save(notification);
        userNotificationProducer.increaseMentionsCount(notifiedUser.getId());
        webSocketClient.send(WebsocketConstants.TOPIC_MENTIONS + notifiedUser.getId(), notificationEvent.getTweetResponse());
    }
}
