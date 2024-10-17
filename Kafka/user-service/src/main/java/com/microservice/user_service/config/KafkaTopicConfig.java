package com.microservice.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${topic.value}")
    private String RANDOM_TOPIC_NAME;

    @Bean
    public NewTopic userTopic() {
        return new NewTopic(RANDOM_TOPIC_NAME, 3, (short) 1);
    }
}
