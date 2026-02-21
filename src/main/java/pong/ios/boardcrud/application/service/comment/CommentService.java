package pong.ios.boardcrud.application.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.comment.CreateCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.DeleteCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.GetCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.UpdateCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.application.port.in.comment.dto.CreateCommentCommand;
import pong.ios.boardcrud.application.port.in.comment.dto.UpdateCommentCommand;
import pong.ios.boardcrud.application.port.out.comment.LoadCommentPort;
import pong.ios.boardcrud.application.port.out.comment.SaveCommentPort;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.domain.comment.CommentStatus;
import pong.ios.boardcrud.domain.comment.CommentStatusCode;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostErrorStatusCode;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService implements
        CreateCommentUseCase,
        UpdateCommentUseCase,
        DeleteCommentUseCase,
        GetCommentUseCase {
    private final LoadCommentPort loadCommentPort;
    private final SaveCommentPort saveCommentPort;
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;
    private final LoadUserPort loadUserPort;
    private final SecurityHolder securityHolder;

    @Override
    @Transactional
    public CommentResult createComment(CreateCommentCommand command) {
        User user = getCurrentUser();
        Post post = getPublishedPost(command.postId());

        Comment parent = null;
        if (command.parentId() != null) {
            parent = loadCommentPort.findById(command.parentId())
                    .orElseThrow(() -> new ApplicationException(CommentStatusCode.COMMENT_PARENT_NOT_FOUND));
            if (!parent.getPost().getId().equals(command.postId())) {
                throw new ApplicationException(CommentStatusCode.COMMENT_PARENT_MISMATCH);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        Comment saved = saveCommentPort.save(
                Comment.builder()
                        .post(post)
                        .user(user)
                        .parent(parent)
                        .content(command.content())
                        .status(CommentStatus.ACTIVE)
                        .likeCount(0)
                        .build()
        );

        post.increaseCommentCount();
        post.updateEditedAt(now);
        savePostPort.save(post);

        return CommentResult.from(saved);
    }

    @Override
    @Transactional
    public CommentResult updateComment(UpdateCommentCommand command) {
        User user = getCurrentUser();
        Comment comment = getComment(command.commentId());
        validateNotDeleted(comment);
        validateWriter(comment, user.getId());

        comment.updateContent(command.content(), LocalDateTime.now());
        return CommentResult.from(saveCommentPort.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        User user = getCurrentUser();
        Comment comment = getComment(commentId);
        validateNotDeleted(comment);

        boolean isWriter = comment.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == UserRoleType.ADMIN;
        if (!isWriter && !isAdmin) {
            throw new ApplicationException(CommentStatusCode.COMMENT_FORBIDDEN);
        }

        LocalDateTime now = LocalDateTime.now();
        comment.softDelete(now);
        saveCommentPort.save(comment);

        Post post = getPublishedPost(comment.getPost().getId());
        post.decreaseCommentCount();
        post.updateEditedAt(now);
        savePostPort.save(post);
    }

    @Override
    public PageResult<CommentResult> getCommentsByPost(Long postId, PageQuery query) {
        getPublishedPost(postId);
        return loadCommentPort.findAllByPostId(postId, query).map(CommentResult::from);
    }

    private Comment getComment(Long commentId) {
        return loadCommentPort.findById(commentId)
                .orElseThrow(() -> new ApplicationException(CommentStatusCode.COMMENT_NOT_FOUND));
    }

    private Post getPublishedPost(Long postId) {
        Post post = loadPostPort.findById(postId)
                .orElseThrow(() -> new ApplicationException(PostErrorStatusCode.POST_NOT_FOUND));
        if (post.getStatus() == PostStatus.DELETED) {
            throw new ApplicationException(PostErrorStatusCode.POST_DELETED);
        }
        return post;
    }

    private User getCurrentUser() {
        Long userId = securityHolder.getCurrentUserId();
        return loadUserPort.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
    }

    private void validateWriter(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new ApplicationException(CommentStatusCode.COMMENT_FORBIDDEN);
        }
    }

    private void validateNotDeleted(Comment comment) {
        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new ApplicationException(CommentStatusCode.COMMENT_DELETED);
        }
    }
}
