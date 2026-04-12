package pong.ios.boardcrud.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.adapter.out.persistence.auth.entity.LoginAttemptEntity;
import pong.ios.boardcrud.adapter.out.persistence.auth.repository.LoginAttemptRepository;
import pong.ios.boardcrud.application.port.out.auth.LoginAttemptPort;
import pong.ios.boardcrud.domain.auth.AuthPersistenceStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.auth.config.AuthProperties;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Transactional
public class LoginAttemptPersistenceAdapter implements LoginAttemptPort {
    private static final int MAX_ATTEMPTS = 5;

    private final LoginAttemptRepository loginAttemptRepository;
    private final AuthProperties authProperties;

    @Override
    public void recordFailure(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now();
            LoginAttemptEntity attempt = loginAttemptRepository.findById(userId)
                    .orElse(LoginAttemptEntity.builder()
                            .userId(userId)
                            .failureCount(0)
                            .build());

            if (attempt.getWindowExpiresAt() == null || attempt.getWindowExpiresAt().isBefore(now)) {
                attempt.openWindow(now.plusMinutes(authProperties.getAttemptWindow()));
            } else {
                attempt.increaseFailure();
            }

            if (attempt.getFailureCount() >= MAX_ATTEMPTS) {
                attempt.lockUntil(now.plusMinutes(authProperties.getLockDuration()));
            }

            loginAttemptRepository.save(attempt);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.LOGIN_ATTEMPT_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBlocked(Long userId) {
        try {
            return loginAttemptRepository.findById(userId)
                    .map(LoginAttemptEntity::getLockedUntil)
                    .filter(lockedUntil -> lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now()))
                    .isPresent();
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.LOGIN_ATTEMPT_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    public void resetAttempt(Long userId) {
        try {
            loginAttemptRepository.deleteById(userId);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.LOGIN_ATTEMPT_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getRemainingLockTime(Long userId) {
        try {
            return loginAttemptRepository.findById(userId)
                    .map(LoginAttemptEntity::getLockedUntil)
                    .filter(lockedUntil -> lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now()))
                    .map(lockedUntil -> {
                        long minutes = Duration.between(LocalDateTime.now(), lockedUntil).toMinutes();
                        return (int) Math.max(1, minutes);
                    })
                    .orElse(0);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.LOGIN_ATTEMPT_PERSISTENCE_ERROR, ex);
        }
    }
}
