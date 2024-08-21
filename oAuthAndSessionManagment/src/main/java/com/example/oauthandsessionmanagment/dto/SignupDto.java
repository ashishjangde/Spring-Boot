package com.example.oauthandsessionmanagment.dto;

import com.example.oauthandsessionmanagment.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
