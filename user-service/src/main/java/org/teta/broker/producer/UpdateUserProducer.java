package org.teta.broker.producer;

import constants.KafkaTopicConstants;
import event.UpdateUserEvent;
import org.teta.mapper.ProducerMapper;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserProducer {

    private final KafkaTemplate<String, UpdateUserEvent> kafkaTemplate;
    private final ProducerMapper producerMapper;

    public void sendUpdateUserEvent(User user) {
        UpdateUserEvent updateUserEvent = producerMapper.toUpdateUserEvent(user);
        kafkaTemplate.send(KafkaTopicConstants.UPDATE_USER_TOPIC, updateUserEvent);
    }
}
