package pong.ios.boardcrud.application.port.out.board;

import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.user.User;

public interface SaveBoardManagerPort {
    Long save(Board board, User user, User granter);
    Long save(Long boardId, Long userId);
}
