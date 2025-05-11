package org.teta.consumer;

import constants.HeaderConstants;
import constants.KafkaTopicConstants;
import event.BlockUserEvent;
import event.FollowRequestUserEvent;
import event.FollowUserEvent;
import event.UpdateUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.teta.service.UserHandlerService;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserHandlerService userHandlerService;

    @KafkaListener(topics = KafkaTopicConstants.UPDATE_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userUpdateListener(UpdateUserEvent userEvent) {
        userHandlerService.handleNewOrUpdateUser(userEvent);
    }

    @KafkaListener(topics = KafkaTopicConstants.BLOCK_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userBlockListener(BlockUserEvent userEvent, @Header(HeaderConstants.AUTH_USER_ID_HEADER) String authId) {
        userHandlerService.handleBlockUser(userEvent, authId);
    }

    @KafkaListener(topics = KafkaTopicConstants.FOLLOW_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userFollowListener(FollowUserEvent userEvent, @Header(HeaderConstants.AUTH_USER_ID_HEADER) String authId) {
        userHandlerService.handleFollowUser(userEvent, authId);
    }

    @KafkaListener(topics = KafkaTopicConstants.FOLLOW_REQUEST_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userFollowRequestListener(FollowRequestUserEvent userEvent, @Header(HeaderConstants.AUTH_USER_ID_HEADER) String authId) {
        userHandlerService.handleFollowUserRequest(userEvent, authId);
    }
}
