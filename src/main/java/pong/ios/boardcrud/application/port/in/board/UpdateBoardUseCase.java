package pong.ios.boardcrud.application.port.in.board;

public interface UpdateBoardUseCase {
    void updateBoard(Long boardId, String name, String type, String description);
}
