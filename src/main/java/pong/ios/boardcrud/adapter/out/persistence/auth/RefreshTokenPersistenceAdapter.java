package pong.ios.boardcrud.adapter.out.persistence.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.adapter.out.persistence.auth.entity.RefreshTokenEntity;
import pong.ios.boardcrud.adapter.out.persistence.auth.repository.RefreshTokenRepository;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.application.port.out.auth.DeleteRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.LoadRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.SaveRefreshTokenPort;
import pong.ios.boardcrud.domain.auth.AuthPersistenceStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.jwt.config.JwtProperties;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class RefreshTokenPersistenceAdapter implements SaveRefreshTokenPort, LoadRefreshTokenPort, DeleteRefreshTokenPort {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Override
    @Deprecated
    public void delete(String refreshToken) {
        try {
            refreshTokenRepository.deleteById(refreshToken);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    @Deprecated
    public void save(String refreshToken) {
        throw new UnsupportedOperationException("userId 없는 refresh token 저장은 지원하지 않음");
    }

    @Override
    @Transactional(readOnly = true)
    @Deprecated
    public boolean exists(String refreshToken) {
        try {
            return refreshTokenRepository.existsById(refreshToken);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    public void save(Long userId, String refreshToken) {
        try {
            refreshTokenRepository.save(RefreshTokenEntity.builder()
                    .refreshToken(refreshToken)
                    .user(UserEntity.builder().id(userId).build())
                    .expiresAt(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()),
                            ZoneId.systemDefault()))
                    .build());
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    public void delete(Long userId, String refreshToken) {
        try {
            refreshTokenRepository.findById(refreshToken)
                    .filter(token -> token.getUser().getId().equals(userId))
                    .ifPresent(refreshTokenRepository::delete);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long userId, String refreshToken) {
        try {
            return refreshTokenRepository.findById(refreshToken)
                    .filter(token -> !token.isExpired(LocalDateTime.now()))
                    .map(token -> token.getUser().getId().equals(userId))
                    .orElse(false);
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }

    @Override
    public List<String> getTokens(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now();
            refreshTokenRepository.deleteAllByUserIdAndExpiresAtBefore(userId, now);

            return refreshTokenRepository.findAllByUserId(userId).stream()
                    .filter(token -> !token.isExpired(now))
                    .map(RefreshTokenEntity::getRefreshToken)
                    .toList();
        } catch (DataAccessException ex) {
            throw new ApplicationException(AuthPersistenceStatusCode.AUTH_TOKEN_PERSISTENCE_ERROR, ex);
        }
    }
}
