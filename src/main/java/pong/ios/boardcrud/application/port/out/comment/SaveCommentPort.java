package pong.ios.boardcrud.application.port.out.comment;

import pong.ios.boardcrud.domain.comment.Comment;

import java.util.List;

public interface SaveCommentPort {
    Comment save(Comment comment);

    void saveAll(List<Comment> comments);
}
