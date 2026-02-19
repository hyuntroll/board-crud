package pong.ios.boardcrud.application.port.out.postdraft;

import pong.ios.boardcrud.domain.post.PostDraft;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

import java.util.Optional;

public interface LoadPostDraftPort {
    Optional<PostDraft> findById(Long draftId);

    PageResult<PostDraft> findAllByUserId(Long userId, PageQuery query);

    PageResult<PostDraft> findAllByUserIdAndBoardId(Long userId, Long boardId, PageQuery query);
}
