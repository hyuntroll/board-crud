package pong.ios.boardcrud.application.service.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.like.LikeCommentUseCase;
import pong.ios.boardcrud.application.port.in.like.LikePostUseCase;
import pong.ios.boardcrud.application.port.in.like.UnlikeCommentUseCase;
import pong.ios.boardcrud.application.port.in.like.UnlikePostUseCase;
import pong.ios.boardcrud.application.port.out.comment.LoadCommentPort;
import pong.ios.boardcrud.application.port.out.comment.SaveCommentPort;
import pong.ios.boardcrud.application.port.out.like.DeleteCommentLikePort;
import pong.ios.boardcrud.application.port.out.like.DeletePostLikePort;
import pong.ios.boardcrud.application.port.out.like.LoadCommentLikePort;
import pong.ios.boardcrud.application.port.out.like.LoadPostLikePort;
import pong.ios.boardcrud.application.port.out.like.SaveCommentLikePort;
import pong.ios.boardcrud.application.port.out.like.SavePostLikePort;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.domain.comment.CommentStatus;
import pong.ios.boardcrud.domain.comment.CommentStatusCode;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostErrorStatusCode;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService implements
        LikePostUseCase,
        UnlikePostUseCase,
        LikeCommentUseCase,
        UnlikeCommentUseCase {
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;
    private final LoadCommentPort loadCommentPort;
    private final SaveCommentPort saveCommentPort;
    private final LoadPostLikePort loadPostLikePort;
    private final SavePostLikePort savePostLikePort;
    private final DeletePostLikePort deletePostLikePort;
    private final LoadCommentLikePort loadCommentLikePort;
    private final SaveCommentLikePort saveCommentLikePort;
    private final DeleteCommentLikePort deleteCommentLikePort;
    private final SecurityHolder securityHolder;

    @Override
    @Transactional
    public void likePost(Long postId) {
        Long userId = securityHolder.getCurrentUserId();
        Post post = getPublishedPost(postId);

        if (loadPostLikePort.exists(postId, userId)) {
            return;
        }

        savePostLikePort.save(postId, userId);
        post.increaseLikeCount();
        savePostPort.save(post);
    }

    @Override
    @Transactional
    public void unlikePost(Long postId) {
        Long userId = securityHolder.getCurrentUserId();
        Post post = getPublishedPost(postId);

        if (!loadPostLikePort.exists(postId, userId)) {
            return;
        }

        deletePostLikePort.delete(postId, userId);
        post.decreaseLikeCount();
        savePostPort.save(post);
    }

    @Override
    @Transactional
    public void likeComment(Long commentId) {
        Long userId = securityHolder.getCurrentUserId();
        Comment comment = getActiveComment(commentId);

        if (loadCommentLikePort.exists(commentId, userId)) {
            return;
        }

        saveCommentLikePort.save(commentId, userId);
        comment.increaseLikeCount();
        saveCommentPort.save(comment);
    }

    @Override
    @Transactional
    public void unlikeComment(Long commentId) {
        Long userId = securityHolder.getCurrentUserId();
        Comment comment = getActiveComment(commentId);

        if (!loadCommentLikePort.exists(commentId, userId)) {
            return;
        }

        deleteCommentLikePort.delete(commentId, userId);
        comment.decreaseLikeCount();
        saveCommentPort.save(comment);
    }

    private Post getPublishedPost(Long postId) {
        Post post = loadPostPort.findById(postId)
                .orElseThrow(() -> new ApplicationException(PostErrorStatusCode.POST_NOT_FOUND));
        if (post.getStatus() == PostStatus.DELETED) {
            throw new ApplicationException(PostErrorStatusCode.POST_DELETED);
        }
        return post;
    }

    private Comment getActiveComment(Long commentId) {
        Comment comment = loadCommentPort.findById(commentId)
                .orElseThrow(() -> new ApplicationException(CommentStatusCode.COMMENT_NOT_FOUND));
        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new ApplicationException(CommentStatusCode.COMMENT_DELETED);
        }
        return comment;
    }
}
