package com.example.oauthandsessionmanagment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private Long  Id;
    private String accessToken;
    private String refreshToken;
}
