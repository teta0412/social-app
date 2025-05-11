package org.teta.broker.producer;

import org.teta.broker.util.ProducerUtil;
import org.teta.constants.KafkaTopicConstants;
import org.teta.event.MuteUserEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MuteUserProducer {

    private final KafkaTemplate<String, MuteUserEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendMuteUserEvent(User user, Long authUserId, boolean hasUserMuted) {
        MuteUserEvent event = producerMapper.toMuteUserEvent(user, hasUserMuted);
        kafkaTemplate.send(ProducerUtil.authHeaderWrapper(KafkaTopicConstants.MUTE_USER_TOPIC, event, authUserId));
    }
}
