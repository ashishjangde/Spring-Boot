package com.microservice.notification_service.consumer;

import com.microservice.user_service.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserKafkaConsumer {



    @KafkaListener(topics = "${kafka.topic.random-topic}")
    public void listenTopic(String message) {
        log.info("Received Message: {}", message);
    }

    @KafkaListener(topics = "${kafka.topic.user-created}" )
    public void listenUserCreated(UserCreatedEvent userCreatedEvent) {
        log.info("Received UserCreatedEvent: {}", userCreatedEvent);
    }

//    @KafkaListener(topics = "${topic.value}")
//    public void listenTopic1(String message) {
//        log.info("Received Message: {}", message);
//    }
//
//    @KafkaListener(topics = "${topic.value}")
//    public void listenTopic2(String message) {
//        log.info("Received Message: {}", message);
//    }
//
//    @KafkaListener(topics = "${topic.value}")
//    public void listenTopic3(String message) {
//        log.info("Received Message: {}", message);
//    }
//
//    @KafkaListener(topics = "${topic.value}")
//    public void listenTopic4(String message) {
//        log.info("Received Message: {}", message);
//    }


}
