package pong.ios.boardcrud.application.port.in.board;

public interface DepriveBoardMangerUseCase {
    void deprive(Long boardId, Long userId);
}
