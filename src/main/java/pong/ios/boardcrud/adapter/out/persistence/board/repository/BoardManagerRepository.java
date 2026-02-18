package pong.ios.boardcrud.adapter.out.persistence.board.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardManagerEntity;

import java.util.List;
import java.util.Optional;

public interface BoardManagerRepository extends JpaRepository<BoardManagerEntity, Long> {
    @EntityGraph(attributePaths = {"board", "user", "granter"})
    Optional<BoardManagerEntity> findByBoard_IdAndUser_Id(Long boardId, Long userId);

    @EntityGraph(attributePaths = {"board", "user", "granter"})
    List<BoardManagerEntity> findAllByBoard_Id(Long boardId);

    boolean existsByBoard_IdAndUser_Id(Long boardId, Long userId);

    void deleteByBoard_IdAndUser_Id(Long boardId, Long userId);
}
