package pong.ios.boardcrud.application.port.in.auth;

public interface LogoutUseCase {
    void logout(String refreshToken);
}
