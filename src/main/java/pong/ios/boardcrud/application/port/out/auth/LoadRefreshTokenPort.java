package pong.ios.boardcrud.application.port.out.auth;


public interface LoadRefreshTokenPort {
    @Deprecated
    boolean exists(String refreshToken);

    boolean exists(Long userId, String refreshToken);
}
