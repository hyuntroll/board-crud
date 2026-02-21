package pong.ios.boardcrud.adapter.out.persistence.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.comment.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Override
    @EntityGraph(attributePaths = {"user", "parent", "post"})
    Optional<CommentEntity> findById(Long id);

    @EntityGraph(attributePaths = {"user", "parent", "post"})
    Page<CommentEntity> findAllByPost_Id(Long postId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "parent", "post"})
    List<CommentEntity> findAllByPost_Id(Long postId);
}
