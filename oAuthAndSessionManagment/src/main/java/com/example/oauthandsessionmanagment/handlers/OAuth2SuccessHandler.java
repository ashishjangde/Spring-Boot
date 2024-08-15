package com.example.oauthandsessionmanagment.handlers;

import com.example.oauthandsessionmanagment.entities.UserEntity;
import com.example.oauthandsessionmanagment.services.serviceImplementation.JWTService;
import com.example.oauthandsessionmanagment.services.serviceImplementation.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JWTService jwtService;

    @Value("${deploy.env}")
    private String deployEnv;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        //log.info( oAuth2User.getAttribute("email"));
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        UserEntity user = userService.getUserByEmail(email);

        if (user == null) {
            UserEntity newUser = UserEntity.builder()
                    .name(name)
                    .email(email)
                    .build();
             user = userService.saveUser(newUser);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv)); // if we are on server mark it true https environment only
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:5173/"; //"http://localhost:5173/home.html?token=" + accessToken;

        //getRedirectStrategy().sendRedirect(request,response,frontEndUrl);

        response.sendRedirect(frontEndUrl);



    }



}
