package org.demo.springsecuritybasic.repositories;

import org.demo.springsecuritybasic.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
