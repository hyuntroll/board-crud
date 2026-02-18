package pong.ios.boardcrud.application.port.in.board;

public interface GrantBoardManagerUseCase {
    void give(Long boardId, Long userId);
}
