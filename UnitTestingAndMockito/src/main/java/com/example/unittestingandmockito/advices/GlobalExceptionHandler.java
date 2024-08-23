package com.example.unittestingandmockito.advices;

import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<APIResponse<?>> handelReturnStatement(APIError apiError){
        return new ResponseEntity<>(new APIResponse<>(apiError), apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subError(errors)
                .build();
        return handelReturnStatement(apiError);
    }


}
