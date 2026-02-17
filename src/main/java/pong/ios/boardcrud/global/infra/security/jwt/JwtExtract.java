package pong.ios.boardcrud.global.infra.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.jwt.exception.JwtStatusCode;
import pong.ios.boardcrud.global.infra.security.jwt.model.TokenType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtExtract {
    private final JwtProvider jwtProvider;

    public String extractTokenFromRequest(HttpServletRequest request) {
        return extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public Authentication getAuthentication(final String token) {
        final Claims claims = jwtProvider.getClaims(token);

        String tokenType = claims.get("token_type", String.class);
        if (!TokenType.ACCESS.toString().equals(tokenType)) {
            throw new ApplicationException(JwtStatusCode.INVALID_TOKEN);
        }

        UserRoleType userType = UserRoleType.valueOf(claims.get("authority", String.class));
        Long userId = Long.parseLong(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                List.of(new SimpleGrantedAuthority(userType.getKey()))
        );
    }

    public String extractToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        throw new ApplicationException(JwtStatusCode.INVALID_TOKEN);
    }


    public void checkTokenType(final Claims claims, final TokenType tokenType) {
        if(!claims.get("token_type").equals(tokenType.toString())) {
            throw new ApplicationException(JwtStatusCode.INVALID_TOKEN, "잘못된 토큰 타입입니다.");
        }
    }
}
