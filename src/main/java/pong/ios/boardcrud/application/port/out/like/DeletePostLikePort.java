package pong.ios.boardcrud.application.port.out.like;

public interface DeletePostLikePort {
    void delete(Long postId, Long userId);
}
