package pong.ios.boardcrud.application.port.in.postdraft;

import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;
import pong.ios.boardcrud.application.port.in.postdraft.dto.SavePostDraftCommand;

public interface SavePostDraftUseCase {
    PostDraftResult saveDraft(SavePostDraftCommand command);
}
