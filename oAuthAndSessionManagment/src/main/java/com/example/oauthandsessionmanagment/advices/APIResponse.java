package com.example.oauthandsessionmanagment.advices;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    @Pattern(regexp = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime localDateTime;
    private T data;
    private APIError error;

    public APIResponse() {
        this.localDateTime = LocalDateTime.now();
    }
    public APIResponse(T data){
        this();
        this.data = data;
    }
    public APIResponse(APIError error){
        this();
        this.error = error;
    }


}
