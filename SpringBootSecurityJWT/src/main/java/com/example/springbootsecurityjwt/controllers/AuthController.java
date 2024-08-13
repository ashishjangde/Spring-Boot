package com.example.springbootsecurityjwt.controllers;


import com.example.springbootsecurityjwt.dto.LoginDto;
import com.example.springbootsecurityjwt.dto.LoginResponseDto;
import com.example.springbootsecurityjwt.dto.SignupDto;
import com.example.springbootsecurityjwt.dto.UserDto;
import com.example.springbootsecurityjwt.services.serviceImplementation.AuthService;
import com.example.springbootsecurityjwt.services.serviceImplementation.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupDto signupDto) {
       UserDto userToSave = userService.signUp(signupDto);
        return ResponseEntity.ok(userToSave);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto,
                                                      HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv)); // if we are on server mark it true https environment only
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto>RefreshUser(HttpServletRequest request ) {
       String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found"));

       LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
