package pong.ios.boardcrud.adapter.out.persistence.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @EntityGraph(attributePaths = {"user", "board", "pinnedBy", "tags"})
    Optional<PostEntity> findById(Long id);
}
