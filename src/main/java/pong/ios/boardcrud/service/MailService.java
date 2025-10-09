package pong.ios.boardcrud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final RedisService redisService;

    public void sendVerificationEmail(String email) {
        String token = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        redisService.saveVerifyEmail(email, token, 600000);
        System.out.println(token);
        // 이메일 보내는 코드
    }

}
