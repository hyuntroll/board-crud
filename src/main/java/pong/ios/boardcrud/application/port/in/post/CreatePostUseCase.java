package pong.ios.boardcrud.application.port.in.post;

import pong.ios.boardcrud.application.port.in.post.dto.CreatePostCommand;
import pong.ios.boardcrud.application.port.in.post.dto.PostResult;

public interface CreatePostUseCase {
    PostResult createPost(CreatePostCommand command);
}
