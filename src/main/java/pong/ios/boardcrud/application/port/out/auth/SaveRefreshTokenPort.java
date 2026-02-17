package pong.ios.boardcrud.application.port.out.auth;

public interface SaveRefreshTokenPort {
    void save(String refreshToken);
}
