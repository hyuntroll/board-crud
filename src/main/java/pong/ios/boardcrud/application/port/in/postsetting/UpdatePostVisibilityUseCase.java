package pong.ios.boardcrud.application.port.in.postsetting;

public interface UpdatePostVisibilityUseCase {
    void updateVisibility(Long postId, boolean isPublic);
}
