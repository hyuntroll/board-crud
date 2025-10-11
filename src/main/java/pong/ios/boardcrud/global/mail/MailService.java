package pong.ios.boardcrud.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pong.ios.boardcrud.global.redis.RedisService;

import java.time.Year;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final RedisService redisService;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String to;

    public void sendVerificationEmail(String email) throws MessagingException {
        String token = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        redisService.saveVerifyEmail(email, token, 600000);
        System.out.println(token);

        Context context = new Context();
        context.setVariable("name", email.split("@")[0]); // 이메일 아이디를 이름처럼 표시
        context.setVariable("code", token);
        context.setVariable("expiryMinutes", 10);
        context.setVariable("actionUrl", "http://localhost:8080/user/verify?email=" + email + "&code=" + token);
        context.setVariable("appName", "Board CRUD");
        context.setVariable("supportEmail", "support@boardcrud.com");
        context.setVariable("year", Year.now().getValue());

        String htmlBody = templateEngine.process("email/verify", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setFrom(email);
        helper.setTo(email);
        helper.setSubject("[Board CRUD] 이메일 인증 코드");
        helper.setText(htmlBody, true);

        mailSender.send(mimeMessage);

    }


}
