package pong.ios.boardcrud.application.port.out.comment;

import pong.ios.boardcrud.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface LoadCommentPort {
    Optional<Comment> findById(Long commentId);

    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByParentId(Long parentId);
}
