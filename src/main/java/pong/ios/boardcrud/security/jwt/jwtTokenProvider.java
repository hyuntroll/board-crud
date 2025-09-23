package pong.ios.boardcrud.security.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class jwtTokenProvider {

    @Value("${spring.jwt.expiration.refresh}")
    private Long refreshExpiredMs;

    @Value("${spring.jwt.expiration.access}")
    private Long accessExpiredMs;

    private final JWTUtil jwtUtil;

    private String createToken(String category, String username, String role, Long expiredMs) {
        return jwtUtil.createJwt(category, username, role, expiredMs);
    }

    public String provideAccessToken(String username, String role) {
        return createToken("access", username, role, accessExpiredMs);
    }

    public String provideRefreshToken(String username, String role) {
        return createToken("refresh", username, role, refreshExpiredMs);
    }

}
