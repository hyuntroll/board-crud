package pong.ios.boardcrud.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void insertNewUser() {
        String name = "너의1";
        String password = "2345";
        String email = "hsm200905219@dgsw.hs.kr";

        UserEntity user = UserEntity.builder()
                .username(name)
                .password(password)
                .email(email)
                .build();

        userRepository.save(user);
    }

}