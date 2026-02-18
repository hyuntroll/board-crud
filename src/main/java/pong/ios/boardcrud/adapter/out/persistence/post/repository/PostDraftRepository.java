package pong.ios.boardcrud.adapter.out.persistence.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostDraftEntity;

import java.util.List;
import java.util.Optional;

public interface PostDraftRepository extends JpaRepository<PostDraftEntity, Long> {
    @EntityGraph(attributePaths = {"user", "board"})
    Optional<PostDraftEntity> findById(Long id);

    @EntityGraph(attributePaths = {"user", "board"})
    List<PostDraftEntity> findAllByUser_Id(Long userId);

    @EntityGraph(attributePaths = {"user", "board"})
    List<PostDraftEntity> findAllByUser_IdAndBoard_Id(Long userId, Long boardId);
}
