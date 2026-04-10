package pong.ios.boardcrud.application.port.out.auth;


import java.util.List;

public interface LoadRefreshTokenPort {
    @Deprecated
    boolean exists(String refreshToken);

    boolean exists(Long userId, String refreshToken);

    List<String> getTokens(Long userId);
}
