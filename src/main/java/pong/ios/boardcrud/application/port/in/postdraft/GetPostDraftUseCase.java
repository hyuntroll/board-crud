package pong.ios.boardcrud.application.port.in.postdraft;

import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;

import java.util.List;

public interface GetPostDraftUseCase {
    PostDraftResult getDraft(Long draftId);

    List<PostDraftResult> getMyDrafts(Long boardId);
}
