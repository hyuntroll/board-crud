package pong.ios.boardcrud.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.entity.user.BoardUser;

@SpringBootTest
class BoardUserRepositoryTest {

    @Autowired
    private BoardUserRepository boardUserRepository;


    @Test
    void insertNewUser() {
        String name = "너의1";
        String password = "2345";
        String email = "hsm200905219@dgsw.hs.kr";

        BoardUser user = BoardUser.builder()
                .username(name)
                .password(password)
                .email(email)
                .build();

        boardUserRepository.save(user);
    }

}