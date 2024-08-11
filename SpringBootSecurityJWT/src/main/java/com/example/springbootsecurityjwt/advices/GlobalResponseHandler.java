package com.example.springbootsecurityjwt.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
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
      String path = request.getURI().getPath();
      if (path.startsWith("/v3/api-docs")||path.startsWith("swagger-ui")) {  //for swagger and api docs
          return body;
      }
      if(body instanceof APIResponse<?>){
          return body;
      }
      return new APIResponse<>(body);
    }
}
