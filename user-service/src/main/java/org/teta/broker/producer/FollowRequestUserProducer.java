package org.teta.broker.producer;

import org.teta.broker.util.ProducerUtil;
import org.teta.constants.KafkaTopicConstants;
import org.teta.event.FollowRequestUserEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowRequestUserProducer {

    private final KafkaTemplate<String, FollowRequestUserEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendFollowRequestUserEvent(User user, Long authUserId, boolean hasUserFollowRequest) {
        FollowRequestUserEvent event = producerMapper.toFollowRequestUserEvent(user, hasUserFollowRequest);
        kafkaTemplate.send(ProducerUtil.authHeaderWrapper(KafkaTopicConstants.FOLLOW_REQUEST_USER_TOPIC, event, authUserId));
    }
}
