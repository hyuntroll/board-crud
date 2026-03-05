package pong.ios.boardcrud.adapter.in.rest.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.domain.comment.CommentStatus;

import java.time.LocalDateTime;

@Schema(description = "댓글 응답")
public record CommentResponse(
        @Schema(description = "댓글 ID", example = "100")
        Long id,
        @Schema(description = "게시글 ID", example = "10")
        Long postId,
        @Schema(description = "작성자 ID", example = "1")
        Long userId,
        @Schema(description = "작성자 닉네임")
        String writerNickname,
        @Schema(description = "작성자 프로필")
        String writerProfile,
        @Schema(description = "부모 댓글 ID", nullable = true)
        Long parentId,
        @Schema(description = "댓글 내용")
        String content,
        @Schema(description = "댓글 상태")
        CommentStatus status,
        @Schema(description = "좋아요 수", example = "0")
        int likeCount,
        @Schema(description = "삭제 시각")
        LocalDateTime deletedAt,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt,
        @Schema(description = "수정 시각")
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
