package com.microservice.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaTopicConfig {

    @Value("${kafka.topic.random-topic}")
    private String RANDOM_TOPIC_NAME;

    @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    @Bean
    public NewTopic userTopic() {
        return new NewTopic(RANDOM_TOPIC_NAME, 3, (short) 1);
    }

    @Bean
    NewTopic UserCreateTopic() {
        return new NewTopic(USER_CREATED_TOPIC, 3, (short) 1);
    }
}
