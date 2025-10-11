package pong.ios.boardcrud.global.security.auth.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.global.redis.RedisService;
import pong.ios.boardcrud.global.util.CookieUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.expiration.refresh}")
    private Long refreshExpiration;

    @Value("${spring.jwt.expiration.access}")
    private Long accessExpiration;

    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    private String createToken(String category, String username, String role, Long expiredMs) {
        return jwtUtil.createJwt(category, username, role, expiredMs);
    }

    public String provideAccessToken(String username, String role) {
        return createToken("access", username, role, accessExpiration);
    }

    public String provideRefreshToken(String username, String role) {
        return createToken("refresh", username, role, refreshExpiration);
    }

    public void accessAndRefreshTokenProvider(HttpServletResponse response, String username, String role) throws IOException {
        String access = provideAccessToken(username, role);
        String refresh = provideRefreshToken(username, role);

        redisService.deleteToken(refresh);
        redisService.saveToken(username, refresh, refreshExpiration);

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", access);
        map.put("token_type", "Bearer");
        map.put("expire_in", jwtUtil.accessExpiration / 1000);
        CookieUtil.createCookie(response, "refresh", refresh, 60 * 60 * 24 * 3600);

        objectMapper.writeValue(response.getWriter(), map);
    }

}
