package pong.ios.boardcrud.adapter.out.redis.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.application.port.out.auth.DeleteRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.LoadRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.SaveRefreshTokenPort;
import pong.ios.boardcrud.global.infra.security.jwt.config.JwtProperties;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RefreshTokenRedisAdapter implements
        SaveRefreshTokenPort,
        LoadRefreshTokenPort,
        DeleteRefreshTokenPort
{
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties jwtProperties;
    private static final String REFRESH_TOKEN_PREFIX = "refresh:";

    @Override
    public void delete(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        redisTemplate.delete(key);
    }

    @Override
    public void delete(Long userId, String refreshToken) {

    }

    @Override
    public void save(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;

        redisTemplate.opsForValue().set(
                key,
                "",
                Duration.ofMillis(jwtProperties.getRefreshExpiration())
        );
    }

    @Override
    public void save(Long userId, String refreshToken) {

    }

    @Override
    public boolean exists(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean exists(Long userId, String refreshToken) {
        return false;
    }
}
