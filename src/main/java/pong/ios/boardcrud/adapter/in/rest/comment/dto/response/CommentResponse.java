package pong.ios.boardcrud.adapter.in.rest.comment.dto.response;

import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.domain.comment.CommentStatus;

import java.time.LocalDateTime;

public record CommentResponse(
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
    public static CommentResponse from(CommentResult result) {
        return new CommentResponse(
                result.id(),
                result.postId(),
                result.userId(),
                result.writerNickname(),
                result.writerProfile(),
                result.parentId(),
                result.content(),
                result.status(),
                result.likeCount(),
                result.deletedAt(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
