package com.example.oauthandsessionmanagment.controllers;



import com.example.oauthandsessionmanagment.dto.LoginDto;
import com.example.oauthandsessionmanagment.dto.LoginResponseDto;
import com.example.oauthandsessionmanagment.dto.SignupDto;
import com.example.oauthandsessionmanagment.dto.UserDto;
import com.example.oauthandsessionmanagment.services.serviceImplementation.AuthService;
import com.example.oauthandsessionmanagment.services.serviceImplementation.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${deploy.env}")
    private String deployEnv;

    private final UserService userService;
    private final AuthService authService;

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
