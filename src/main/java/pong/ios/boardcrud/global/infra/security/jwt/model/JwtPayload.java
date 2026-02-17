package pong.ios.boardcrud.global.infra.security.jwt.model;

public record JwtPayload(
        String accessToken,
        String refreshToken
) {}
