package com.example.springbootsecurityjwt;

import com.example.springbootsecurityjwt.entities.UserEntity;
import com.example.springbootsecurityjwt.services.serviceImplementation.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootSecurityJwtApplicationTests {


	@Autowired
	private JWTService jwtService;

	@Test
	void contextLoads() {
//		UserEntity user = new UserEntity(89L,"ashishjangdex@gmail.com","123678");
//
//		String token = jwtService.generateAccessToken(user);
//
//		System.out.println(token);
//
//		Long verifyToken = jwtService.getUserIdFromToken(token);
//		System.out.println(verifyToken);

	}

}
