package com.example.oauthandsessionmanagment.services.serviceImplementation;


import com.example.oauthandsessionmanagment.dto.SignupDto;
import com.example.oauthandsessionmanagment.dto.UserDto;
import com.example.oauthandsessionmanagment.entities.UserEntity;
import com.example.oauthandsessionmanagment.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException{
        return userRepositories.findByEmail(username).orElseThrow(() -> new BadCredentialsException("User With This " + username + " Not Found"));
    }

    public UserEntity getUserById(Long userId){
        return userRepositories.findById(userId).orElseThrow(() -> new BadCredentialsException("User With This " + userId));
    }

    public UserEntity getUserByEmail(String email){
        return userRepositories.findByEmail(email).orElse(null);
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


    public UserEntity saveUser(UserEntity user) {
        return userRepositories.save(user);
    }
}
