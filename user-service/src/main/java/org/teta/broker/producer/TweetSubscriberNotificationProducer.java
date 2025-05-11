package org.teta.broker.producer;

import org.teta.constants.KafkaTopicConstants;
import org.teta.event.TweetSubscriberNotificationEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetSubscriberNotificationProducer {

    private final KafkaTemplate<String, TweetSubscriberNotificationEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendTweetSubscriberNotificationEvent(TweetSubscriberNotificationEvent notificationEvent, List<User> subscribers) {
        TweetSubscriberNotificationEvent event = producerMapper.toTweetSubscriberNotificationEvent(notificationEvent, subscribers);
        kafkaTemplate.send(KafkaTopicConstants.SEND_TWEET_SUBSCRIBER_NOTIFICATION_TOPIC, event);
    }
}
