package com.example.unittestingandmockito.advices;

import com.example.unittestingandmockito.exceptions.EmployeeValidationException;
import com.example.unittestingandmockito.exceptions.ExistingResourceFoundException;
import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @ExceptionHandler(ExistingResourceFoundException.class)
    public ResponseEntity<APIResponse<?>> handleExistingResourceFoundException(ExistingResourceFoundException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, String>> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());

        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subError(errors)
                .build();

        return handelReturnStatement(apiError);
    }


    @ExceptionHandler(EmployeeValidationException.class)
    public ResponseEntity<APIResponse<?>> handleEmployeeValidationException(EmployeeValidationException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subError(e.getErrors())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }




}
