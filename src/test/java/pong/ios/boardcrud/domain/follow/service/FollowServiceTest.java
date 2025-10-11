package pong.ios.boardcrud.domain.follow.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FollowService.class)
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Test
    public void follow() {
        String u1 = "admin1";
        String u2 = "admin14";

        followService.follow(u1, u2);

    }
}