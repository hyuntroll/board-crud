package pong.ios.boardcrud.application.port.out.postdraft;

import pong.ios.boardcrud.domain.post.PostDraft;

public interface SavePostDraftPort {
    PostDraft save(PostDraft postDraft);
}
