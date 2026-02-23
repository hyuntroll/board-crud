package pong.ios.boardcrud.application.port.out.like;

public interface DeleteCommentLikePort {
    void delete(Long commentId, Long userId);
}
