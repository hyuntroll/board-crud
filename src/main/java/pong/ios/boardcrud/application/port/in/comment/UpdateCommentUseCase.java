package pong.ios.boardcrud.application.port.in.comment;

import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.application.port.in.comment.dto.UpdateCommentCommand;

public interface UpdateCommentUseCase {
    CommentResult updateComment(UpdateCommentCommand command);
}
