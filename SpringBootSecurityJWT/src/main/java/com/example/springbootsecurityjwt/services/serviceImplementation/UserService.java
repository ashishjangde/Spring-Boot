package com.example.springbootsecurityjwt.services.serviceImplementation;

import com.example.springbootsecurityjwt.dto.LoginDto;
import com.example.springbootsecurityjwt.dto.SignupDto;
import com.example.springbootsecurityjwt.dto.UserDto;
import com.example.springbootsecurityjwt.entities.UserEntity;
import com.example.springbootsecurityjwt.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepositories userRepositories;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositories.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User With This" + username + "Not Found"));
    }

    public UserDto signUp(SignupDto signupDto) {
        Optional<UserEntity> userEntity = userRepositories.findByEmail(signupDto.getEmail());
        if (userEntity.isPresent()) {
            throw new BadCredentialsException("User with this email already exists");
        }
       UserEntity user = modelMapper.map(signupDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepositories.save(user);
        return modelMapper.map(user, UserDto.class);
    }


}
