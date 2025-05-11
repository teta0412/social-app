package org.teta.broker.producer;

import constants.KafkaTopicConstants;
import event.FollowUserNotificationEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowUserNotificationProducer {

    private final KafkaTemplate<String, FollowUserNotificationEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendFollowUserNotificationEvent(User authUser, User notifiedUser) {
        FollowUserNotificationEvent event = producerMapper.toUserNotificationEvent(authUser, notifiedUser);
        kafkaTemplate.send(KafkaTopicConstants.SEND_USER_NOTIFICATION_TOPIC, event);
    }
}
