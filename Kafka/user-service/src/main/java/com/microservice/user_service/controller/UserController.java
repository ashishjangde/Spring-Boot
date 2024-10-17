package com.microservice.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.value}")
    private String RANDOM_TOPIC_NAME;

    @PostMapping("{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        kafkaTemplate.send(RANDOM_TOPIC_NAME, message);
        return ResponseEntity.ok(message);
    }
}
