package pong.ios.boardcrud.application.port.in.comment;

import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;

import java.util.List;

public interface GetCommentUseCase {
    List<CommentResult> getCommentsByPost(Long postId);

    List<CommentResult> getReplies(Long commentId);
}
