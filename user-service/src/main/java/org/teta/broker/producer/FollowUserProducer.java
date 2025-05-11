package org.teta.broker.producer;

import org.teta.broker.util.ProducerUtil;
import org.teta.constants.KafkaTopicConstants;
import org.teta.event.FollowUserEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowUserProducer {

    private final KafkaTemplate<String, FollowUserEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendFollowUserEvent(User user, Long authUserId, boolean hasUserFollowed) {
        FollowUserEvent event = producerMapper.toFollowUserEvent(user, hasUserFollowed);
        kafkaTemplate.send(ProducerUtil.authHeaderWrapper(KafkaTopicConstants.FOLLOW_USER_TOPIC, event, authUserId));
    }
}
