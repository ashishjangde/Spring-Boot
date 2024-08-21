package com.example.oauthandsessionmanagment.advices;



import com.example.oauthandsessionmanagment.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
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
                .message(e.getMessage())
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

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponse<?>> handleJwtException(JwtException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<?>> handleAuthenticationException(AuthenticationException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<?>> handleBadCredentialsException(BadCredentialsException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.FORBIDDEN)
                .message("Access denied: " + e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handelAccessDeniedException(AccessDeniedException e) {
        APIError apiError = APIError.builder()
                .status(HttpStatus.FORBIDDEN)
                .message("Acess denied " + e.getMessage())
                .build();
        return handelReturnStatement(apiError);
    }



}
