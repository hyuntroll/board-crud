package pong.ios.boardcrud.adapter.out.persistence.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.like.entity.PostLikeEntity;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    boolean existsByPost_IdAndUser_Id(Long postId, Long userId);

    void deleteByPost_IdAndUser_Id(Long postId, Long userId);
}
