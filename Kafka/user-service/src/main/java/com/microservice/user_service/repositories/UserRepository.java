package com.microservice.user_service.repositories;

import com.microservice.user_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
}
