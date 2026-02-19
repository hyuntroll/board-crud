package pong.ios.boardcrud.application.port.in.board;

public interface DepriveBoardManagerUseCase {
    void deprive(Long boardId, Long userId);
}
