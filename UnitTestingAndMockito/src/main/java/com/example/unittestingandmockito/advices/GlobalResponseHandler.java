package com.example.unittestingandmockito.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String uri = request.getURI().toString();
        if (uri.startsWith("/v3/api-docs")||uri.startsWith("/swagger-ui")) {
            return body;
        }
        if (body instanceof APIResponse<?> ){
            return body;
        }else{
            return new APIResponse<>(body);
        }

    }
}
