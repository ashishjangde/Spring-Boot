package com.microservice.user_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreatedEvent {
    private String name;
    private String email;
}
