package pong.ios.boardcrud.global.infra.security.jwt.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT 토큰 응답")
public record JwtPayload(
        @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1Ni...")
        String accessToken,

        @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1Ni...")
        String refreshToken
) {}
