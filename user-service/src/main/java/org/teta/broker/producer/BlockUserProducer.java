package org.teta.broker.producer;

import org.teta.broker.util.ProducerUtil;
import constants.KafkaTopicConstants;
import event.BlockUserEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockUserProducer {

    private final KafkaTemplate<String, BlockUserEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendBlockUserEvent(User user, Long authUserId, boolean hasUserBlocked) {
        BlockUserEvent event = producerMapper.toBlockUserEvent(user, hasUserBlocked);
        kafkaTemplate.send(ProducerUtil.authHeaderWrapper(KafkaTopicConstants.BLOCK_USER_TOPIC, event, authUserId));
    }
}
