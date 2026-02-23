package pong.ios.boardcrud.adapter.out.persistence.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.like.entity.CommentLikeEntity;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    boolean existsByComment_IdAndUser_Id(Long commentId, Long userId);

    void deleteByComment_IdAndUser_Id(Long commentId, Long userId);
}
