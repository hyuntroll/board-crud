package pong.ios.boardcrud.application.port.out.auth;

public interface DeleteRefreshTokenPort {
    void delete(String refreshToken);
}