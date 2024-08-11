package org.demo.springsecuritybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain springSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       return   httpSecurity
//                 .formLogin(Customizer.withDefaults())//csrf not needed when we have website on another server
////                                               // we will use jwt this is for understanding how spring security work
//                 .build();
//            httpSecurity
//                    .formLogin(formConfig ->formConfig.loginPage("/login.html"));  // if we had a login page in template folder
         httpSecurity
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/posts").permitAll()  //without auth anyone can ** for all
                         .requestMatchers("/posts/{postId}").hasAnyRole("ADMIN")
                         .anyRequest().authenticated())
                 .csrf(csrfConfig->csrfConfig.disable()) // for disabling csrf token
                 .sessionManagement(sessionConfig->sessionConfig
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS));  //now session will be stateless
//                 .formLogin(Customizer.withDefaults());



          return httpSecurity.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails adminUser = User
                .withUsername("ashish")
                .password(passwordEncoder().encode("jangde"))
                .roles("ADMIN")
                .build();
        UserDetails normalUser = User
                .withUsername("Jasmine")
                .password(passwordEncoder().encode("chouhan"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
