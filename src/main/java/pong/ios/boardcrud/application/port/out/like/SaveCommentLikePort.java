package pong.ios.boardcrud.application.port.out.like;

public interface SaveCommentLikePort {
    void save(Long commentId, Long userId);
}
