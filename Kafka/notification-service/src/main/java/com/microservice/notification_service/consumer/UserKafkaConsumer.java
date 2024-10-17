package com.microservice.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {



    @KafkaListener(topics = "${topic.value}")
    public void listenTopic(String message) {
        log.info("Received Message: {}", message);
    }
}
