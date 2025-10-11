package pong.ios.boardcrud.global.security.auth.serivce;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.user.repository.UserRepository;
import pong.ios.boardcrud.global.security.auth.dto.JoinRequest;
import pong.ios.boardcrud.global.mail.MailService;
import pong.ios.boardcrud.global.redis.RedisService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final MailService mailService;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private long mailTimeout;

    public boolean singUp(JoinRequest joinRequest) {
        String username = joinRequest.getUsername();
        String email = joinRequest.getEmail();
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        if (userRepository.existsByUsername(username) ||
                userRepository.existsByEmail(email)) { return false; }

        redisService.saveTempUser(joinRequest, mailTimeout);
        try {
            mailService.sendVerificationEmail(email);
        }
        catch ( MessagingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean verifyEmail(String email, String code) {
        if (!redisService.verifyEmailCode(email, code)) return false;

        Optional<JoinRequest> optionalDto = redisService.findTempUser(email);
        if (optionalDto.isPresent()) {
            JoinRequest joinRequest = optionalDto.get();
            UserEntity user = UserEntity.builder()
                    .username(joinRequest.getUsername())
                    .password(joinRequest.getPassword())
                    .email(joinRequest.getEmail())
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);

            redisService.deleteVerifyEmail(email);
            redisService.deleteTempUser(joinRequest);
            return true;
        }

        return false;
    }
}
