package pong.ios.boardcrud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.refresh.RefreshToken;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(RefreshToken token) {
        String refreshToken = token.getRefreshToken();
        redisTemplate.opsForValue().set(
                "token:" + refreshToken,
                token.getUsername()
        );
        redisTemplate.expire("token" + refreshToken, token.getExpireMs(), TimeUnit.MILLISECONDS);
    }

    public void saveToken(String username, String refreshToken, long expireMs) {
        redisTemplate.opsForValue().set(
                "token:" + refreshToken,
                username
        );
        redisTemplate.expire("token:" + refreshToken, expireMs, TimeUnit.MILLISECONDS);
    }

    public Optional<RefreshToken> findByToken(String token) {
        String username = redisTemplate.opsForValue().get("token:" + token);

        if (Objects.isNull(username)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(username, token));
    }

    public void deleteToken(String token) {
        redisTemplate.delete("token:" + token);
    }


}
