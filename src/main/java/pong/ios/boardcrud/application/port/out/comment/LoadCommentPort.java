package pong.ios.boardcrud.application.port.out.comment;

import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

import java.util.List;
import java.util.Optional;

public interface LoadCommentPort {
    Optional<Comment> findById(Long commentId);

    PageResult<Comment> findAllByPostId(Long postId, PageQuery query);

    List<Comment> findAllByPostId(Long postId);
}
