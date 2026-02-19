package pong.ios.boardcrud.application.port.in.postdraft;

import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

public interface GetPostDraftUseCase {
    PostDraftResult getDraft(Long draftId);

    PageResult<PostDraftResult> getMyDrafts(Long boardId, PageQuery query);
}
