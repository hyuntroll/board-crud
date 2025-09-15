package pong.ios.boardcrud.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;


@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertNewPost() {
        UserEntity user = userRepository.findById(1L).get();
        Post post = new Post("새로운 문서", "이거 진짜임?", user);

        postRepository.save(post);
    }

    @Test
    void findById() {
        Post post = postRepository.findById(1L).get();

        System.out.println(post.getWriter().getUsername());
    }

}