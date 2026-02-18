package pong.ios.boardcrud.application.port.out.auth;

public interface LoginAttemptPort {
    void recordFailure(Long userId);
    boolean isBlocked(Long userId);
    void resetAttempt(Long userId);
    int getRemainingLockTime(Long userId);
}
