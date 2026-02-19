package pong.ios.boardcrud.application.port.out.board;

import pong.ios.boardcrud.domain.board.Board;

public interface SaveBoardPort {
    Board save(Board board);
}
