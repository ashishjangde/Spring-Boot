package com.example.unittestingandmockito;

import com.example.unittestingandmockito.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UnitTestingAndMockitoApplication implements CommandLineRunner {

    @Value("${my.variable}")
    public String myVariable;

    public static void main(String[] args) {
        SpringApplication.run(UnitTestingAndMockitoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("my variable: " + myVariable);
    }
}
