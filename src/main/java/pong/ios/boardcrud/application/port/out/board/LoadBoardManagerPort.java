package pong.ios.boardcrud.application.port.out.board;

import pong.ios.boardcrud.domain.board.BoardManager;

import java.util.List;
import java.util.Optional;

public interface LoadBoardManagerPort {
    Optional<BoardManager> findById(Long boardId, Long userId);
    List<BoardManager> findAll(Long boardId);
    boolean existsById(Long boardId, Long userId);
}
