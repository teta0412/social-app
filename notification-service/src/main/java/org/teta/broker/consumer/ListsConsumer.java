package org.teta.broker.consumer;

import org.teta.constants.KafkaTopicConstants;
import org.teta.event.UpdateListsEvent;
import org.teta.service.ListsHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListsConsumer {

    private final ListsHandlerService listsHandlerService;

    @KafkaListener(topics = KafkaTopicConstants.UPDATE_LISTS_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void listsUpdateListener(UpdateListsEvent listsEvent) {
        listsHandlerService.handleUpdateList(listsEvent);
    }
}
