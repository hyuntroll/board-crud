package pong.ios.boardcrud.adapter.out.redis.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.application.port.out.auth.LoginAttemptPort;
import pong.ios.boardcrud.global.infra.auth.config.AuthProperties;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LoginAttemptAdapter implements LoginAttemptPort {
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthProperties authProperties;

    private static final int MAX_ATTEMPTS = 5;
    private static final String FAIL_PREFIX = "login:fail:user:";
    private static final String LOCK_PREFIX = "login:lock:user:";

    @Override
    public void recordFailure(Long userId) {
        String key = FAIL_PREFIX + userId;
        Long count = stringRedisTemplate.opsForValue().increment(key);

        if (count == 1) {
            stringRedisTemplate.expire(key, Duration.ofMinutes(authProperties.getAttemptWindow()));
        }

        if (count >= MAX_ATTEMPTS) {
            stringRedisTemplate.opsForValue().set(
                    LOCK_PREFIX + userId,
                    "locked",
                    Duration.ofMinutes(authProperties.getLockDuration()));
        }
    }

    @Override
    public boolean isBlocked(Long userId) {
        return stringRedisTemplate.opsForValue().get(LOCK_PREFIX + userId) != null;
    }

    @Override
    public void resetAttempt(Long userId) {
        stringRedisTemplate.delete(LOCK_PREFIX + userId);
        stringRedisTemplate.delete(FAIL_PREFIX + userId);
    }

    @Override
    public int getRemainingLockTime(Long userId) {
        Long ttl = stringRedisTemplate.getExpire(LOCK_PREFIX + userId, TimeUnit.MINUTES);
        return ttl > 0 ? ttl.intValue() : 0;
    }
}
