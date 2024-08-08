package org.demo.autoconfiganddependencyinjection.config;

import org.demo.autoconfiganddependencyinjection.car.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    Car newcar(){
        return new Car();
    }
}
