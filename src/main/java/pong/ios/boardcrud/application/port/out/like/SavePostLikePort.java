package pong.ios.boardcrud.application.port.out.like;

public interface SavePostLikePort {
    void save(Long postId, Long userId);
}
