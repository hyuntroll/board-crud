package pong.ios.boardcrud.application.port.in.postsetting;

public interface UpdatePostCommentPolicyUseCase {
    void updateCommentPolicy(Long postId, boolean isCommentAllowed);
}
