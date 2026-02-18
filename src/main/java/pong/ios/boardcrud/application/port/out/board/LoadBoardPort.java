package pong.ios.boardcrud.application.port.out.board;

import pong.ios.boardcrud.domain.board.Board;

import java.util.Optional;

public interface LoadBoardPort {
    Optional<Board> findById(Long id);
}
