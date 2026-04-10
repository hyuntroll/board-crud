package pong.ios.boardcrud.application.port.in.comment.dto;

import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.domain.comment.CommentStatus;

import java.time.LocalDateTime;

public record CommentResult(
        Long id,
        Long postId,
        Long userId,
        String writerNickname,
        String writerProfile,
        Long parentId,
        String content,
        CommentStatus status,
        int likeCount,
        LocalDateTime deletedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResult from(Comment comment) {
        return new CommentResult(
                comment.getId(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getUser().getProfile(),
                comment.getParent() == null ? null : comment.getParent().getId(),
                comment.getContent(),
                comment.getStatus(),
                comment.getLikeCount(),
                comment.getDeletedAt(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
