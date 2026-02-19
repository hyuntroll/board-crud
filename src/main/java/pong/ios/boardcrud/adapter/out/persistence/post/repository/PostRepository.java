package pong.ios.boardcrud.adapter.out.persistence.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.domain.post.PostStatus;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @EntityGraph(attributePaths = {"user", "tags"})
    Optional<PostEntity> findById(Long id);

    @EntityGraph(attributePaths = {"user", "tags"})
    List<PostEntity> findAllByStatusOrderByCreatedAtDesc(PostStatus status);

    @EntityGraph(attributePaths = {"user", "tags"})
    List<PostEntity> findAllByBoard_IdAndStatusOrderByCreatedAtDesc(Long boardId, PostStatus status);
}
