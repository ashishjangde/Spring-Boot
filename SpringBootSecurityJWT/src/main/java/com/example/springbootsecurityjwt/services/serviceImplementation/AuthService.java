package com.example.springbootsecurityjwt.services.serviceImplementation;
import com.example.springbootsecurityjwt.dto.LoginDto;
import com.example.springbootsecurityjwt.dto.LoginResponseDto;
import com.example.springbootsecurityjwt.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.BadCredentialsException;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            UserEntity user = (UserEntity) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new  LoginResponseDto(user.getId(), accessToken, refreshToken );

        } catch (BadCredentialsException e) {
            // Handle wrong password
            throw new BadCredentialsException("Wrong password");
        }
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user =  userService.getUserById(userId);
        String accessToken = jwtService.generateAccessToken(user);

        return new  LoginResponseDto(userId, accessToken, refreshToken);
    }
}
