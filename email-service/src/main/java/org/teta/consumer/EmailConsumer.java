package org.teta.consumer;


import event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.teta.service.EmailService;

import static constants.KafkaTopicConstants.SEND_EMAIL_TOPIC;


@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = SEND_EMAIL_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void sendEmailListener(SendEmailEvent sendEmailEvent) {
        emailService.sendEmail(sendEmailEvent);
    }
}
