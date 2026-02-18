package pong.ios.boardcrud.application.port.out.postdraft;

import pong.ios.boardcrud.domain.post.PostDraft;

import java.util.List;
import java.util.Optional;

public interface LoadPostDraftPort {
    Optional<PostDraft> findById(Long draftId);

    List<PostDraft> findAllByUserId(Long userId);

    List<PostDraft> findAllByUserIdAndBoardId(Long userId, Long boardId);
}
