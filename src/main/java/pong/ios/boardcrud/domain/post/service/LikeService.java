package pong.ios.boardcrud.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.post.domain.Like;
import pong.ios.boardcrud.domain.post.domain.LikeId;
import pong.ios.boardcrud.domain.post.dto.LikeResponse;
import pong.ios.boardcrud.domain.post.repository.LikeRepository;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.post.repository.PostRepository;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public LikeResponse likePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) throw new NoSuchElementException();
        UserEntity user = optionalUser.get();

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (!likeRepository.existsByLikerAndPost(user, post)) {
            LikeId likeId = new LikeId(post.getId(), user.getId());
            likeRepository.save(new Like(likeId, user, post));
        }

        return new LikeResponse(id, countPostLike(id));

    }

    public LikeResponse unlikePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) throw new NoSuchElementException();
        UserEntity user = optionalUser.get();

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (likeRepository.existsByLikerAndPost(user, post)) {
            likeRepository.deleteByLikerAndPost(user, post);
        }

        return new LikeResponse(id, countPostLike(id));

    }

    public int countPostLike(Long postId) throws NoSuchElementException {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        return likeRepository.countByPost(post);
    }
}
