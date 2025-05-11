package org.teta.broker.consumer;

import constants.KafkaTopicConstants;
import event.UpdateTweetEvent;
import org.teta.service.TweetHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetConsumer {

    private final TweetHandlerService tweetHandlerService;

    @KafkaListener(topics = KafkaTopicConstants.UPDATE_TWEET_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void tweetUpdateListener(UpdateTweetEvent tweetEvent) {
        tweetHandlerService.handleUpdateTweet(tweetEvent);
    }
}
