package pong.ios.boardcrud.adapter.out.redis.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.application.port.out.auth.DeleteRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.LoadRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.SaveRefreshTokenPort;
import pong.ios.boardcrud.global.infra.security.jwt.config.JwtProperties;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RefreshTokenRedisAdapter implements
        SaveRefreshTokenPort,
        LoadRefreshTokenPort,
        DeleteRefreshTokenPort
{
    private final StringRedisTemplate redisTemplate;
    private final JwtProperties jwtProperties;

    private static final String REFRESH_TOKEN_PREFIX = "refresh:";
    private static final String USER_TOKEN_SET_PREFIX = "user_tokens:";

    @Override
    public void delete(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        redisTemplate.delete(key);
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
    public boolean exists(String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void save(Long userId, String refreshToken) {
        Duration ttl = Duration.ofMillis(jwtProperties.getRefreshExpiration());

        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + refreshToken,
                userId.toString(),
                ttl
        );

        // 2. userId별 토큰 목록 Set에 추가
        String setKey = USER_TOKEN_SET_PREFIX + userId;
        redisTemplate.opsForSet().add(setKey, refreshToken);
        redisTemplate.expire(setKey, ttl.plusDays(1)); // 여유있게
    }

    @Override
    public void delete(Long userId, String refreshToken) {
        // 1. 토큰 자체 삭제
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + refreshToken);

        // 2. Set 에서도 제거
        String setKey = USER_TOKEN_SET_PREFIX + userId;
        redisTemplate.opsForSet().remove(setKey, refreshToken);
    }

    @Override
    public boolean exists(Long userId, String refreshToken) {
        String value = redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + refreshToken);
        return value != null && value.equals(userId.toString());
    }

    @Override
    public List<String> getTokens(Long userId) {
        String setKey = USER_TOKEN_SET_PREFIX + userId;
        Set<String> tokens = redisTemplate.opsForSet().members(setKey);

        if (tokens == null || tokens.isEmpty()) {
            return new ArrayList<>();
        }

        // 토큰 키 생성
        List<String> tokenKeys = tokens.stream()
                .map(t -> REFRESH_TOKEN_PREFIX + t)
                .toList();

        // multi-Get 으로 한 번에 조회
        List<String> values = redisTemplate.opsForValue().multiGet(tokenKeys);

        // 유효/만료 토큰 정리
        List<String> validTokens = new ArrayList<>();
        List<String> expiredTokens = new ArrayList<>();
        List<String> tokenList = new ArrayList<>(tokens.stream()
                .map(String::valueOf)
                .toList());

        for (int i=0; i < tokenList.size(); i++) {
            if (values != null && values.get(i) != null) {
                validTokens.add(tokenList.get(i));
            } else {
                expiredTokens.add(tokenList.get(i));
            }
        }

        // 만료된 토큰 일괄 제거
        if (!expiredTokens.isEmpty()) {
            redisTemplate.opsForSet().remove(setKey, expiredTokens.toArray());
        }

        return validTokens;
    }
}