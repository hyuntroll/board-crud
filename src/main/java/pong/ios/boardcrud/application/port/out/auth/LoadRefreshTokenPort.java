package pong.ios.boardcrud.application.port.out.auth;


public interface LoadRefreshTokenPort {
    boolean exists(String refreshToken);
}
