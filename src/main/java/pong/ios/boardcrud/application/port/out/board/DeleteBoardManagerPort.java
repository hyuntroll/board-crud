package pong.ios.boardcrud.application.port.out.board;

public interface DeleteBoardManagerPort {
    void delete(Long boardId, Long userId);
}
