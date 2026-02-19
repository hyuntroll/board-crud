package pong.ios.boardcrud.application.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.postdraft.DeletePostDraftUseCase;
import pong.ios.boardcrud.application.port.in.postdraft.GetPostDraftUseCase;
import pong.ios.boardcrud.application.port.in.postdraft.SavePostDraftUseCase;
import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;
import pong.ios.boardcrud.application.port.in.postdraft.dto.SavePostDraftCommand;
import pong.ios.boardcrud.application.port.out.board.LoadBoardPort;
import pong.ios.boardcrud.application.port.out.postdraft.DeletePostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.LoadPostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.SavePostDraftPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.domain.post.PostDraft;
import pong.ios.boardcrud.domain.post.PostDraftStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostDraftService implements
        SavePostDraftUseCase,
        GetPostDraftUseCase,
        DeletePostDraftUseCase {
    private final SavePostDraftPort savePostDraftPort;
    private final LoadPostDraftPort loadPostDraftPort;
    private final DeletePostDraftPort deletePostDraftPort;
    private final LoadUserPort loadUserPort;
    private final LoadBoardPort loadBoardPort;
    private final SecurityHolder securityHolder;

    @Override
    @Transactional
    public PostDraftResult saveDraft(SavePostDraftCommand command) {
        Long userId = securityHolder.getCurrentUserId();
        User user = getUser(userId);
        Board board = getBoard(command.boardId());

        PostDraft baseDraft = command.draftId() == null
                ? null
                : getMyDraft(command.draftId());

        LocalDateTime now = LocalDateTime.now();
        PostDraft saved;
        if (baseDraft == null) {
            saved = savePostDraftPort.save(
                    PostDraft.builder()
                            .user(user)
                            .board(board)
                            .title(command.title())
                            .content(command.content())
                            .savedAt(now)
                            .build()
            );
        } else {
            baseDraft.updateDraft(board, command.title(), command.content(), now);
            saved = savePostDraftPort.save(baseDraft);
        }

        return PostDraftResult.from(saved);
    }

    @Override
    public PostDraftResult getDraft(Long draftId) {
        return PostDraftResult.from(getMyDraft(draftId));
    }

    @Override
    public PageResult<PostDraftResult> getMyDrafts(Long boardId, PageQuery query) {
        Long userId = securityHolder.getCurrentUserId();
        if (boardId == null) {
            return loadPostDraftPort.findAllByUserId(userId, query).map(PostDraftResult::from);
        }

        getBoard(boardId);
        return loadPostDraftPort.findAllByUserIdAndBoardId(userId, boardId, query).map(PostDraftResult::from);
    }

    @Override
    @Transactional
    public void deleteDraft(Long draftId) {
        getMyDraft(draftId);
        deletePostDraftPort.delete(draftId);
    }

    private PostDraft getMyDraft(Long draftId) {
        Long userId = securityHolder.getCurrentUserId();
        PostDraft draft = loadPostDraftPort.findById(draftId)
                .orElseThrow(() -> new ApplicationException(PostDraftStatusCode.POST_DRAFT_NOT_FOUND));
        if (!draft.getUser().getId().equals(userId)) {
            throw new ApplicationException(PostDraftStatusCode.POST_DRAFT_FORBIDDEN);
        }
        return draft;
    }

    private User getUser(Long userId) {
        return loadUserPort.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
    }

    private Board getBoard(Long boardId) {
        Board board = loadBoardPort.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        if (!board.isActive()) {
            throw new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND);
        }
        return board;
    }
}
