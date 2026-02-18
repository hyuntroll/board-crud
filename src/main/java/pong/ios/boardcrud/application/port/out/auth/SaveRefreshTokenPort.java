package pong.ios.boardcrud.application.port.out.auth;

public interface SaveRefreshTokenPort {
    @Deprecated
    void save(String refreshToken);

    void save(Long userId, String refreshToken);
}
