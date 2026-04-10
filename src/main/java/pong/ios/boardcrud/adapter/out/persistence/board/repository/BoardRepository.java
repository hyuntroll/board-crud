package pong.ios.boardcrud.adapter.out.persistence.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
