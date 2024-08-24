package com.example.unittestingandmockito;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {   // go to documentation https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }
}
