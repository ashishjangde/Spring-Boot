package com.microservice.user_service.controller;

import com.microservice.user_service.dto.UserDto;
import com.microservice.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;

    @Value("${kafka.topic.random-topic}")
    private String RANDOM_TOPIC_NAME;

    @PostMapping("{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
//        for (int i = 0; i < 1000; i++) {
//            kafkaTemplate.send(RANDOM_TOPIC_NAME, message + i);
//        }
        kafkaTemplate.send(RANDOM_TOPIC_NAME, message);
        return ResponseEntity.ok("message sent to topic: " + RANDOM_TOPIC_NAME);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
}
