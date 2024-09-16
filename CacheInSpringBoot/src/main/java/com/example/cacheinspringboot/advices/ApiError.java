package com.example.cacheinspringboot.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<Map<String,String>> subError;
}
