package pong.ios.boardcrud.adapter.in.rest.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.report.dto.PostReportResult;
import pong.ios.boardcrud.domain.report.PostReportReason;
import pong.ios.boardcrud.domain.report.PostReportStatus;

import java.time.LocalDateTime;

@Schema(description = "게시글 신고 응답")
public record PostReportResponse(
        @Schema(description = "신고 ID", example = "1")
        Long id,
        @Schema(description = "게시글 ID", example = "10")
        Long postId,
        @Schema(description = "신고자 ID", example = "2")
        Long reporterId,
        @Schema(description = "신고자 아이디", example = "user")
        String reporterUsername,
        @Schema(description = "신고자 닉네임", example = "유저")
        String reporterNickname,
        @Schema(description = "신고 사유")
        PostReportReason reason,
        @Schema(description = "상세 설명")
        String description,
        @Schema(description = "처리 상태")
        PostReportStatus status,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt,
        @Schema(description = "수정 시각")
        LocalDateTime updatedAt
) {
    public static PostReportResponse from(PostReportResult result) {
        return new PostReportResponse(
                result.id(),
                result.postId(),
                result.reporterId(),
                result.reporterUsername(),
                result.reporterNickname(),
                result.reason(),
                result.description(),
                result.status(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
