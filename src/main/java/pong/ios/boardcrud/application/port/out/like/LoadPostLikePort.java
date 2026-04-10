package pong.ios.boardcrud.application.port.out.like;

public interface LoadPostLikePort {
    boolean exists(Long postId, Long userId);
}
