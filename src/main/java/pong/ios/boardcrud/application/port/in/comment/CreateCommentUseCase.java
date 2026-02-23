package pong.ios.boardcrud.application.port.in.comment;

import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.application.port.in.comment.dto.CreateCommentCommand;

public interface CreateCommentUseCase {
    CommentResult createComment(CreateCommentCommand command);
}
