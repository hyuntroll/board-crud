package pong.ios.boardcrud.application.port.out.auth;

public interface DeleteRefreshTokenPort {
    @Deprecated
    void delete(String refreshToken);

    void delete(Long userId, String refreshToken);
}