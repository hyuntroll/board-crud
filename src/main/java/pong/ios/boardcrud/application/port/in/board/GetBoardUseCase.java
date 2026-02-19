package pong.ios.boardcrud.application.port.in.board;

import pong.ios.boardcrud.adapter.in.rest.board.dto.response.BoardResponse;

public interface GetBoardUseCase {
    BoardResponse getBoard(Long boardId);
}
