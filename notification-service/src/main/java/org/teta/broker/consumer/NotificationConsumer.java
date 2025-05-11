package org.teta.broker.consumer;

import org.teta.constants.KafkaTopicConstants;

import org.teta.event.*;
import org.teta.service.NotificationHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationHandlerService notificationHandlerService;

    @KafkaListener(topics = KafkaTopicConstants.SEND_LISTS_NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listsNotificationListener(ListsNotificationEvent notificationEvent) {
        notificationHandlerService.handleListsNotification(notificationEvent);
    }

    @KafkaListener(topics = KafkaTopicConstants.SEND_USER_NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void followUserNotificationListener(FollowUserNotificationEvent notificationEvent) {
        notificationHandlerService.handleFollowUserNotification(notificationEvent);
    }

    @KafkaListener(topics = KafkaTopicConstants.SEND_TWEET_NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void tweetNotificationListener(TweetNotificationEvent notificationEvent) {
        notificationHandlerService.handleTweetNotification(notificationEvent);
    }

    @KafkaListener(topics = KafkaTopicConstants.SEND_TWEET_SUBSCRIBER_NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void tweetSubscriberNotificationListener(TweetSubscriberNotificationEvent notificationEvent) {
        notificationHandlerService.handleTweetSubscriberNotification(notificationEvent);
    }

    @KafkaListener(topics = KafkaTopicConstants.SEND_TWEET_MENTION_NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void tweetMentionNotificationListener(TweetMentionNotificationEvent notificationEvent) {
        notificationHandlerService.handleTweetMentionNotification(notificationEvent);
    }
}
