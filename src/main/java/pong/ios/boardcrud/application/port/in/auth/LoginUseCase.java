package pong.ios.boardcrud.application.port.in.auth;

import pong.ios.boardcrud.global.infra.security.jwt.model.JwtPayload;

public interface LoginUseCase {
    JwtPayload login(String loginId, String password);
}
