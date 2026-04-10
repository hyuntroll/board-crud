package pong.ios.boardcrud.application.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.adapter.in.rest.board.dto.response.BoardResponse;
import pong.ios.boardcrud.application.port.in.board.CreateBoardUseCase;
import pong.ios.boardcrud.application.port.in.board.DeleteBoardUseCase;
import pong.ios.boardcrud.application.port.in.board.GetBoardUseCase;
import pong.ios.boardcrud.application.port.in.board.UpdateBoardUseCase;
import pong.ios.boardcrud.application.port.out.board.LoadBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService implements
        CreateBoardUseCase,
        DeleteBoardUseCase,
        GetBoardUseCase,
        UpdateBoardUseCase {
    private final LoadBoardManagerPort loadBoardManagerPort;
    private final LoadBoardPort loadBoardPort;
    private final SaveBoardPort saveBoardPort;
    private final SecurityHolder securityHolder;
    private final LoadUserPort loadUserPort;
    private final SaveBoardManagerPort saveBoardManagerPort;


    @Override
    @Transactional
    public Long createBoard(String name, String type, String description) {
        Board board = saveBoardPort.save(
            Board.builder()
                .name(name)
                .type(type)
                .description(description)
                .isActive(true)
                .build()
        );
        User user = loadUserPort.findById(securityHolder.getCurrentUserId())
            .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
        saveBoardManagerPort.save(board, user, user);

        return board.getId();
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = loadBoardPort.findById(boardId)
            .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        Long userId = securityHolder.getCurrentUserId();
        validateManager(boardId, userId);
        validateBoardIsActive(board);
        board.deactivate();
        saveBoardPort.save(board);
    }

    @Override
    public BoardResponse getBoard(Long boardId) {
        Board board = loadBoardPort.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        validateBoardIsActive(board);

        return BoardResponse.from(board, loadBoardManagerPort.findAll(boardId));
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, String name, String type, String description) {
        Board board = loadBoardPort.findById(boardId)
            .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        Long userId = securityHolder.getCurrentUserId();
        validateManager(boardId, userId);
        validateBoardIsActive(board);
        board.updateInfo(name, type, description);
        saveBoardPort.save(board);
    }

    private void validateManager(Long boardId, Long userId) {
        if (!loadBoardManagerPort.existsById(boardId, userId)) {
            throw new ApplicationException(BoardStatusCode.BOARD_MANAGER_FORBIDDEN);
        }
    }

    private void validateBoardIsActive(Board board) {
        if (!board.isActive()) {
            throw new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND);
        }
    }
}
