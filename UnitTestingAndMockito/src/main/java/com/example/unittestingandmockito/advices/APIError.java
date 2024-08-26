package com.example.unittestingandmockito.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class APIError {
    private HttpStatus status;
    private String message;
    private List<Map<String,String>> subError;
}
