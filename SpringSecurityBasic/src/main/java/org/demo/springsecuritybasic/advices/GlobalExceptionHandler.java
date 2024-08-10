package org.demo.springsecuritybasic.advices;

import org.demo.springsecuritybasic.exception.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<APIResponse<?>> handelReturnStatement(APIError error){
        return new ResponseEntity<>(new APIResponse<>(error), error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> resourceNotFound(ResourceNotFoundException e) {
        APIError apiError =  APIError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Resource not found")
                .subError(Collections.singletonList(e.getMessage()))
                .build();
       // return new ResponseEntity<>(new APIResponse<>(apiError), HttpStatus.NOT_FOUND);
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)  //map(error->error.getDefaultMessage())
                .collect(Collectors.toList());

        APIError error = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subError(errors)
                .build();
        return handelReturnStatement(error);
    }
}
