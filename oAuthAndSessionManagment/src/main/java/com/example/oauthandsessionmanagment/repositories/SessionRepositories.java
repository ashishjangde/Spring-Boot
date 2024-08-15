package com.example.oauthandsessionmanagment.repositories;

import com.example.oauthandsessionmanagment.entities.SessionEntity;
import com.example.oauthandsessionmanagment.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepositories extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity>findByUser (UserEntity user);

   Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
