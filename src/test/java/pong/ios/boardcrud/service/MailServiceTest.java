package pong.ios.boardcrud.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    JavaMailSender mailSender;

    @Test
    public void sendMail() throws MessagingException {
        String to = "hsm20090529@dgsw.hs.kr";
        String subject = "인증코드입니다";
        String htmlBody = """
        <!DOCTYPE html>
        <html xmlns:th="http://www.thymeleaf.org">
        <head>
          <meta charset="UTF-8" />
          <title>이메일 인증</title>
        </head>
        <body>
          <h2>Board CRUD 이메일 인증</h2>
          <p>안녕하세요, <span>사용자</span>님!</p>
          <p>아래 버튼을 클릭해서 인증을 완료해주세요:</p>
          <a  style="background:#007bff;color:white;padding:10px 20px;text-decoration:none;">
            이메일 인증하기
          </a>
        </body>
        </html>
        """;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("hsm200905292@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(mimeMessage);

    }


}