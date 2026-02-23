package pong.ios.boardcrud.adapter.in.rest.postdraft.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;

import java.time.LocalDateTime;

@Schema(description = "임시저장 응답")
public record PostDraftResponse(
        @Schema(description = "임시저장 ID", example = "1")
        Long id,
        @Schema(description = "유저 ID", example = "1")
        Long userId,
        @Schema(description = "게시판 ID", example = "1")
        Long boardId,
        @Schema(description = "제목")
        String title,
        @Schema(description = "내용")
        String content,
        @Schema(description = "임시저장 시각")
        LocalDateTime savedAt,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt
) {
    public static PostDraftResponse from(PostDraftResult result) {
        return new PostDraftResponse(
                result.id(),
                result.userId(),
                result.boardId(),
                result.title(),
                result.content(),
                result.savedAt(),
                result.createdAt()
        );
    }
}
