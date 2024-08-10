package org.demo.springsecuritybasic.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class APIError {
   private HttpStatus status;
   private String message;
   private List<String> subError;

}
