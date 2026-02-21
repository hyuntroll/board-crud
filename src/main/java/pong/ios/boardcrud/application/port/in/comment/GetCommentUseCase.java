package pong.ios.boardcrud.application.port.in.comment;

import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

public interface GetCommentUseCase {
    PageResult<CommentResult> getCommentsByPost(Long postId, PageQuery query);
}
