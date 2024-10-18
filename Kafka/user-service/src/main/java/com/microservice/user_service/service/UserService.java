package com.microservice.user_service.service;

import com.microservice.user_service.dto.UserDto;
import com.microservice.user_service.entities.UserEntity;
import com.microservice.user_service.event.UserCreatedEvent;
import com.microservice.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        UserCreatedEvent userEvent = modelMapper.map(userEntity, UserCreatedEvent.class);
        kafkaTemplate.send(USER_CREATED_TOPIC,userEntity.getId() ,userEvent);
        return modelMapper.map(userEntity, UserDto.class);
    }
}
