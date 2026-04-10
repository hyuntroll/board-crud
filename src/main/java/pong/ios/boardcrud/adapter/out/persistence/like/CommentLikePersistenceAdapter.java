package pong.ios.boardcrud.adapter.out.persistence.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.comment.entity.CommentEntity;
import pong.ios.boardcrud.adapter.out.persistence.like.entity.CommentLikeEntity;
import pong.ios.boardcrud.adapter.out.persistence.like.repository.CommentLikeRepository;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.application.port.out.like.DeleteCommentLikePort;
import pong.ios.boardcrud.application.port.out.like.LoadCommentLikePort;
import pong.ios.boardcrud.application.port.out.like.SaveCommentLikePort;

@Component
@RequiredArgsConstructor
public class CommentLikePersistenceAdapter implements LoadCommentLikePort, SaveCommentLikePort, DeleteCommentLikePort {
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public boolean exists(Long commentId, Long userId) {
        return commentLikeRepository.existsByComment_IdAndUser_Id(commentId, userId);
    }

    @Override
    public void save(Long commentId, Long userId) {
        commentLikeRepository.save(
                CommentLikeEntity.builder()
                        .comment(CommentEntity.builder().id(commentId).build())
                        .user(UserEntity.builder().id(userId).build())
                        .build()
        );
    }

    @Override
    public void delete(Long commentId, Long userId) {
        commentLikeRepository.deleteByComment_IdAndUser_Id(commentId, userId);
    }
}
