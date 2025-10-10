package pong.ios.boardcrud.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.follow.domain.Follow;
import pong.ios.boardcrud.domain.follow.domain.FollowId;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.follow.repository.FollowRepository;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository boardUserRepository;

    @Test
    void following() {
        UserEntity user1 = boardUserRepository.findById(1L).get();
        UserEntity user2 = boardUserRepository.findById(4L).get();

        FollowId followId = new FollowId(user1.getId(), user2.getId());
        Follow follow = new Follow(followId, user1, user2);

        followRepository.save(follow);

    }

}