package pong.ios.boardcrud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.global.auth.refresh.RefreshToken;
import pong.ios.boardcrud.global.auth.dto.JoinRequest;

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

    public void saveVerifyEmail(String email, String code, long expireMs) {
        redisTemplate.opsForValue().set(
                "email:" + email,
                code
        );
        redisTemplate.expire("email:" + email, expireMs, TimeUnit.MILLISECONDS);
    }

    public boolean verifyEmailCode(String email, String code) {
        String stored = redisTemplate.opsForValue().get("email:" + email);

        return Objects.nonNull(stored) && stored.equals(code);
    }

    public void deleteVerifyEmail(String email) {
        redisTemplate.delete("email:" + email);
    }


    public void saveTempUser(JoinRequest dto, long expireMs) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set(
                    "user:" + dto.getEmail(),
                    json
            );
            redisTemplate.expire("user:" + dto.getEmail(), expireMs, TimeUnit.MILLISECONDS);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTempUser(JoinRequest dto) {
        redisTemplate.delete("user:" + dto.getEmail());
    }

    public Optional<JoinRequest> findTempUser(String email) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String json = redisTemplate.opsForValue().get("user:" + email);
            if (Objects.isNull(json)) return Optional.empty();

            return Optional.of(objectMapper.readValue(json, JoinRequest.class));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
