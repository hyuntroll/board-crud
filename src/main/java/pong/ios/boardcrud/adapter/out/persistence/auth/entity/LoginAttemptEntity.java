package pong.ios.boardcrud.adapter.out.persistence.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_login_attempts")
public class LoginAttemptEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "failure_count", nullable = false)
    private int failureCount;

    @Column(name = "window_expires_at")
    private LocalDateTime windowExpiresAt;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    public void reset() {
        this.failureCount = 0;
        this.windowExpiresAt = null;
        this.lockedUntil = null;
    }

    public void openWindow(LocalDateTime windowExpiresAt) {
        this.failureCount = 1;
        this.windowExpiresAt = windowExpiresAt;
        this.lockedUntil = null;
    }

    public void increaseFailure() {
        this.failureCount += 1;
    }

    public void lockUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }
}
