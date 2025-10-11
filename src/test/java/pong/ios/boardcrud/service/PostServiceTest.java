package pong.ios.boardcrud.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pong.ios.boardcrud.domain.post.service.PostService;
import pong.ios.boardcrud.domain_1.entity.comment.Comment;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.global.auth.repository.CommentRepository;
import pong.ios.boardcrud.domain.post.repository.PostRepository;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void addComment() {
        UserEntity user = userRepository.findByUsername("니얼굴").get();
        Post post = postRepository.findById(2L).get();
        Comment comment = new Comment("이건 좀", user, post);
        post.getComments().add(comment);

        commentRepository.save(comment);

    }

    @Test
    @Transactional
    void testFindComment() {
        Post post = postRepository.findById(2L).get();

        for (Comment comment : post.getComments()) {
            System.out.println(comment.getContent());
        }
    }

/*
    @Test
    void LikePost() {

        postService.likePost(2L, "니얼굴1");
        postService.likePost(3L, "니얼굴1");
        postService.likePost(4L, "니얼굴1");

        postService.likePost(2L, "니얼굴");
        postService.likePost(3L, "니얼굴");
        postService.likePost(4L, "니얼굴");

        postService.likePost(2L, "니");
        postService.likePost(3L, "니");
        postService.likePost(4L, "니");

    }

    @Test
    void testLike() {

        System.out.println(postService.countPostLike(5L));
        System.out.println(postService.countPostLike(2L));
        System.out.println(postService.countPostLike(3L));

    }
*/
}