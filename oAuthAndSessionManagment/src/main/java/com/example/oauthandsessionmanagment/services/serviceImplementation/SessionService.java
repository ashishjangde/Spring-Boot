package com.example.oauthandsessionmanagment.services.serviceImplementation;

import com.example.oauthandsessionmanagment.entities.SessionEntity;
import com.example.oauthandsessionmanagment.entities.UserEntity;
import com.example.oauthandsessionmanagment.repositories.SessionRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepositories sessionRepositories;
    private final int SESSION_LIMIT = 2;

    public void GenerateNewSession(UserEntity user , String refreshToken) {
        List<SessionEntity> userSession = sessionRepositories.findByUser(user);
        if (userSession.size() == SESSION_LIMIT) {
           userSession.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
           SessionEntity lastRecentlyUsedSession = userSession.getFirst();
           sessionRepositories.delete(lastRecentlyUsedSession);
        }
        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
         sessionRepositories.save(newSession);
    }

    public void validateSession(String refreshToken) {
      SessionEntity sessionEntity = sessionRepositories.findByRefreshToken(refreshToken)
              .orElseThrow(() -> new SessionAuthenticationException("Invalid refresh token"));
      sessionEntity.setLastUsedAt(LocalDateTime.now());
    }
}
