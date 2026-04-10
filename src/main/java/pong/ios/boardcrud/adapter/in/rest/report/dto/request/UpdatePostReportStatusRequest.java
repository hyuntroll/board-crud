package pong.ios.boardcrud.adapter.in.rest.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pong.ios.boardcrud.domain.report.PostReportStatus;

@Schema(description = "신고 상태 변경 요청")
public record UpdatePostReportStatusRequest(
        @Schema(description = "신고 처리 상태", example = "IN_PROGRESS")
        @NotNull(message = "상태는 필수입니다.")
        PostReportStatus status
) {
}
