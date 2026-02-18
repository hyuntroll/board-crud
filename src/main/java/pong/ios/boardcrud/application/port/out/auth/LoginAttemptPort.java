package pong.ios.boardcrud.application.port.out.auth;

public interface LoginAttemptPort {
    void recordFailure(String loginId);
    boolean isBlocked(String loginId);
    void resetAttempt(String loginId);
    int getFailure(String loginId);
}
