package org.teta.broker.producer;

import org.teta.constants.KafkaTopicConstants;
import org.teta.enums.NotificationType;
import org.teta.event.TweetMentionNotificationEvent;
import org.teta.event.TweetNotificationEvent;
import org.teta.event.TweetSubscriberNotificationEvent;
import org.teta.mapper.TweetNotificationMapper;
import org.teta.model.Tweet;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetNotificationProducer {

    private final KafkaTemplate<String, TweetNotificationEvent> tweetNotificationTemplate;
    private final KafkaTemplate<String, TweetSubscriberNotificationEvent> tweetSubscriberNotificationTemplate;
    private final KafkaTemplate<String, TweetMentionNotificationEvent> tweetMentionNotificationTemplate;
    private final TweetNotificationMapper mapper;

    public void sendTweetNotificationEvent(NotificationType type, Tweet tweet, User authUser, boolean condition) {
        TweetNotificationEvent event = mapper.toTweetNotificationEvent(type, tweet, authUser, condition);
        tweetNotificationTemplate.send(KafkaTopicConstants.SEND_TWEET_NOTIFICATION_TOPIC, event);
    }

    public void sendTweetSubscriberNotificationEvent(Tweet tweet, User authUser) {
        TweetSubscriberNotificationEvent event = mapper.toTweetSubscriberNotificationEvent(tweet, authUser);
        tweetSubscriberNotificationTemplate.send(KafkaTopicConstants.SEND_SUBSCRIBER_NOTIFICATION_TOPIC, event);
    }

    public void sendTweetMentionNotificationEvent(NotificationType notificationType, Tweet tweet, User user, User authUser) {
        TweetMentionNotificationEvent event = mapper.toTweetMentionNotificationEvent(notificationType, tweet, user, authUser);
        tweetMentionNotificationTemplate.send(KafkaTopicConstants.SEND_TWEET_MENTION_NOTIFICATION_TOPIC, event);
    }
}
