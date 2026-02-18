package pong.ios.boardcrud.application.port.in.board;

public interface CreateBoardUseCase {

    Long createBoard(String name, String type, String description);
}
