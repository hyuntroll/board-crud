package pong.ios.boardcrud.adapter.out.persistence.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.auth.entity.RefreshTokenEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    boolean existsByRefreshTokenAndUserId(String refreshToken, Long userId);
    List<RefreshTokenEntity> findAllByUserId(Long userId);
    void deleteAllByUserIdAndExpiresAtBefore(Long userId, LocalDateTime now);
}
