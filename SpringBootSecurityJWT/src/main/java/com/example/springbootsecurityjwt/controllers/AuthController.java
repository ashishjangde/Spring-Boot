package com.example.springbootsecurityjwt.controllers;


import com.example.springbootsecurityjwt.advices.APIResponse;
import com.example.springbootsecurityjwt.dto.LoginDto;
import com.example.springbootsecurityjwt.dto.SignupDto;
import com.example.springbootsecurityjwt.dto.UserDto;
import com.example.springbootsecurityjwt.services.serviceImplementation.AuthService;
import com.example.springbootsecurityjwt.services.serviceImplementation.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupDto signupDto) {
       UserDto userToSave = userService.signUp(signupDto);
        return ResponseEntity.ok(userToSave);
    }

    @PostMapping("/signin")
    public ResponseEntity<APIResponse<String>> loginUser(@Valid @RequestBody LoginDto loginDto,
                                                         HttpServletRequest request ,HttpServletResponse response) {
        String token = authService.login(loginDto);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        APIResponse<String> responseToken = new APIResponse<>(token);
        return ResponseEntity.ok(responseToken);
    }
}
