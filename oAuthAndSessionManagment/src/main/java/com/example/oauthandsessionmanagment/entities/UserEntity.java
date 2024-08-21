package com.example.oauthandsessionmanagment.entities;

import com.example.oauthandsessionmanagment.entities.enums.Role;
import com.example.oauthandsessionmanagment.utils.PermissionMappings;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "userDetails")
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
//
//1. @ElementCollection
//    Purpose: The @ElementCollection annotation is used to map a collection of basic or embeddable types in an entity. This means that the collection is not a separate entity, but is instead a part of the parent entity.
//    Use Case: In your code, it is used to map the Set<Role> where Role is likely an enum. The roles are stored as part of the entity, meaning they do not have their own identity or table, but are instead stored as a collection in the table of the parent entity.
//            2. fetch = FetchType.EAGER
//    Purpose: The fetch attribute specifies how the data should be loaded from the database.
//            FetchType.EAGER: When you specify FetchType.EAGER, it means that the roles collection will be loaded immediately when the parent entity is loaded. The collection will be fetched along with the entity in a single query or multiple queries (depending on how JPA decides to execute it).
//    Why Use EAGER: Eager fetching is typically used when you know that you will always need the collection data whenever you load the entity. For example, if the roles are always needed for authorization purposes immediately after loading the entity, eager fetching might be justified.
//3. @Enumerated(EnumType.STRING)
//    Purpose: The @Enumerated(EnumType.STRING) annotation specifies how the enum values should be stored in the database.
//    EnumType.STRING: This indicates that the enum values should be stored as strings in the database (e.g., ADMIN, USER). This is generally preferred because it makes the database more readable and is safer if the enum constants' ordinal positions change.
//    Why Use @ElementCollection with FetchType.EAGER
//    Scenario: If you have a User entity with roles that define the permissions or access levels, these roles need to be checked as soon as the user is loaded (e.g., during authentication or authorization). In such a scenario, eager fetching ensures that the roles are immediately available without needing a separate query later.
//
//    Trade-offs: While eager fetching can be useful, it can lead to performance issues if the collection is large or if it's not always needed. It can cause unnecessary data to be loaded, leading to increased memory usage and slower query performance.

//@ElementCollection(fetch = FetchType.EAGER)
//@Enumerated(EnumType.STRING)
//private Set<Permission> permissions;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role-> new SimpleGrantedAuthority("Role_"+role.name()))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(permission ->
//                authorities.add(new SimpleGrantedAuthority(permission.getName())));
//
//        return authorities;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
           Set<SimpleGrantedAuthority> permission = PermissionMappings.getSimpleGrantedAuthoritySet(role);
           authorities.addAll(permission);
           authorities.add(new SimpleGrantedAuthority("Role_"+role.name()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
