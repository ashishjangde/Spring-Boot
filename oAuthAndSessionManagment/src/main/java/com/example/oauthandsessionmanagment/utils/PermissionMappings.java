package com.example.oauthandsessionmanagment.utils;

import com.example.oauthandsessionmanagment.entities.enums.Permissions;
import com.example.oauthandsessionmanagment.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.oauthandsessionmanagment.entities.enums.Permissions.*;
import static com.example.oauthandsessionmanagment.entities.enums.Role.*;

public class PermissionMappings {

    private static final Map<Role, Set<Permissions>> permissionMap = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE, POST_CREATE),
            ADMIN, Set.of(USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE, USER_CREATE, POST_CREATE, POST_DELETE, USER_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getSimpleGrantedAuthoritySet(Role role) {
        return permissionMap.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
