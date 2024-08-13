package com.example.springbootsecurityjwt.services.serviceImplementation;

import com.example.springbootsecurityjwt.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;


@Service
public class JWTService {

    @Value("${jwt.securityKey}")
    private String securityKey;

    private SecretKey getSecretKey(){

        return Keys.hmacShaKeyFor(securityKey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(String.valueOf(userEntity.getId()))
                .claim("email", userEntity.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(String.valueOf(userEntity.getId()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 12))
                .signWith(getSecretKey())
                .compact();
    }


    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.getSubject());
    }

}
