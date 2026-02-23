package pong.ios.boardcrud.application.port.out.like;

public interface LoadCommentLikePort {
    boolean exists(Long commentId, Long userId);
}
