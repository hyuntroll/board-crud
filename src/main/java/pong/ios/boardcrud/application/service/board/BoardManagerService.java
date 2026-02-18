package pong.ios.boardcrud.application.service.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.board.DepriveBoardMangerUseCase;
import pong.ios.boardcrud.application.port.in.board.GrantBoardManagerUseCase;
import pong.ios.boardcrud.application.port.out.board.DeleteBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardManagerPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardManagerService implements GrantBoardManagerUseCase, DepriveBoardMangerUseCase {
    private final LoadUserPort loadUserPort;
    private final LoadBoardManagerPort loadBoardManagerPort;
    private final LoadBoardPort loadBoardPort;
    private final SaveBoardManagerPort saveBoardManagerPort;
    private final DeleteBoardManagerPort deleteBoardManagerPort;
    private final SecurityHolder securityHolder;

    @Override
    public void deprive(Long boardId, Long userId) {
        validateBoardExists(boardId);
        validateManager(boardId, securityHolder.getCurrentUserId());

        if (!loadBoardManagerPort.existsById(boardId, userId)) {
            throw new ApplicationException(BoardStatusCode.BOARD_MANAGER_NOT_FOUND);
        }

        deleteBoardManagerPort.delete(boardId, userId);
    }

    @Override
    public void give(Long boardId, Long userId) {
        validateBoardExists(boardId);
        validateManager(boardId, securityHolder.getCurrentUserId());

        if (loadBoardManagerPort.existsById(boardId, userId)) {
            throw new ApplicationException(BoardStatusCode.ALREADY_EXISTS_GRANT);
        }

        Board board = loadBoardPort.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        User user = loadUserPort.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
        User granter = loadUserPort.findById(securityHolder.getCurrentUserId())
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));

        saveBoardManagerPort.save(board, user, granter);
    }

    private void validateBoardExists(Long boardId) {
        Board board = loadBoardPort.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        if (!board.isActive()) {
            throw new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND);
        }
    }

    private void validateManager(Long boardId, Long userId) {
        if (!loadBoardManagerPort.existsById(boardId, userId)) {
            throw new ApplicationException(BoardStatusCode.BOARD_MANAGER_FORBIDDEN);
        }
    }
}
