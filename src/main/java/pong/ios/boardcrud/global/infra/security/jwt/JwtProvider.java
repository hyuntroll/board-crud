package pong.ios.boardcrud.global.infra.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.jwt.config.JwtProperties;
import pong.ios.boardcrud.global.infra.security.jwt.exception.JwtStatusCode;
import pong.ios.boardcrud.global.infra.security.jwt.model.TokenType;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey key;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
        catch (ExpiredJwtException e) {
            throw new ApplicationException(JwtStatusCode.EXPIRED_TOKEN);
        }
        catch (IllegalArgumentException e) {
            throw new ApplicationException(JwtStatusCode.INVALID_TOKEN);
        }
        catch (MalformedJwtException e) {
            throw new ApplicationException(JwtStatusCode.INVALID_TOKEN, "JWT 토큰 형식이 올바르지 않습니다.");
        }
    }

    public JwtBuilder getJwtsBuilder(TokenType tokenType, String id, long expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(id)
                .claim("token_type", tokenType.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration, ChronoUnit.MILLIS)))
                .signWith(key, Jwts.SIG.HS256);
    }

    public String generateAccessToken(Long id, long userId, UserRoleType role) {
        return getJwtsBuilder(
                    TokenType.ACCESS,
                    id.toString(),
                    jwtProperties.getAccessExpiration()
                )
                .claim("authority", role.name())
                .claim("userId", userId)
                .compact();

    }

    public String generateRefreshToken(String id) {
        return getJwtsBuilder(
                TokenType.REFRESH,
                id.toString(),
                jwtProperties.getRefreshExpiration()
        ).compact();
    }
}
