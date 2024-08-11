package org.demo.springsecuritybasic.services.serviceImplementation;

import lombok.RequiredArgsConstructor;
import org.demo.springsecuritybasic.repositories.UserRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserDetailsService {

    private final UserRepositories userRepositories;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositories.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User With This" + username + "Not Found"));
    }
}
