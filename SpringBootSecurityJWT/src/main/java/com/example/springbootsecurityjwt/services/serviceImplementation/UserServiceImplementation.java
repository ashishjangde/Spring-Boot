package com.example.springbootsecurityjwt.services.serviceImplementation;

import com.example.springbootsecurityjwt.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserDetailsService {

    private final UserRepositories userRepositories;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositories.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User With This" + username + "Not Found"));
    }
}
