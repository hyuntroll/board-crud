package pong.ios.boardcrud.adapter.out.persistence.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.like.entity.PostLikeEntity;
import pong.ios.boardcrud.adapter.out.persistence.like.repository.PostLikeRepository;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.application.port.out.like.DeletePostLikePort;
import pong.ios.boardcrud.application.port.out.like.LoadPostLikePort;
import pong.ios.boardcrud.application.port.out.like.SavePostLikePort;

@Component
@RequiredArgsConstructor
public class PostLikePersistenceAdapter implements LoadPostLikePort, SavePostLikePort, DeletePostLikePort {
    private final PostLikeRepository postLikeRepository;

    @Override
    public boolean exists(Long postId, Long userId) {
        return postLikeRepository.existsByPost_IdAndUser_Id(postId, userId);
    }

    @Override
    public void save(Long postId, Long userId) {
        postLikeRepository.save(
                PostLikeEntity.builder()
                        .post(PostEntity.builder().id(postId).build())
                        .user(UserEntity.builder().id(userId).build())
                        .build()
        );
    }

    @Override
    public void delete(Long postId, Long userId) {
        postLikeRepository.deleteByPost_IdAndUser_Id(postId, userId);
    }
}
