package pong.ios.boardcrud.adapter.in.rest.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.post.dto.PostResult;

import java.time.LocalDateTime;

@Schema(description = "게시글 목록 요약 응답")
public record PostSummary(
        @Schema(description = "게시글 ID", example = "10")
        Long id,
        @Schema(description = "게시판 ID", example = "1")
        Long boardId,
        @Schema(description = "제목")
        String title,
        @Schema(description = "카테고리")
        String category,
        @Schema(description = "작성자 닉네임")
        String writerNickname,
        @Schema(description = "상단 고정 여부", example = "false")
        boolean isPinned,
        @Schema(description = "조회수", example = "12")
        int viewCount,
        @Schema(description = "좋아요 수", example = "3")
        int likeCount,
        @Schema(description = "댓글 수", example = "2")
        int commentCount,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt,
        @Schema(description = "수정 시각")
        LocalDateTime updatedAt
) {
    public static PostSummary from(PostResult result) {
        return new PostSummary(
                result.id(),
                result.boardId(),
                result.title(),
                result.category(),
                result.writerNickname(),
                result.isPinned(),
                result.viewCount(),
                result.likeCount(),
                result.commentCount(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
