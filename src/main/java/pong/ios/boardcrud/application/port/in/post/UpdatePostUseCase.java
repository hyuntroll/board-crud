package pong.ios.boardcrud.application.port.in.post;

import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.application.port.in.post.dto.UpdatePostCommand;

public interface UpdatePostUseCase {
    PostResult updatePost(UpdatePostCommand command);
}
