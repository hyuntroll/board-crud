package pong.ios.boardcrud.adapter.in.rest.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.report.dto.CreatePostReportCommand;
import pong.ios.boardcrud.domain.report.PostReportReason;

@Schema(description = "게시글 신고 요청")
public record CreatePostReportRequest(
        @Schema(description = "신고 사유", example = "SPAM")
        @NotNull(message = "신고 사유는 필수입니다.")
        PostReportReason reason,

        @Schema(description = "상세 설명", example = "스팸 링크가 포함되어 있습니다.")
        @Size(max = 1000, message = "상세 설명은 1000자 이하여야 합니다.")
        String description
) {
    public CreatePostReportCommand toCommand() {
        return new CreatePostReportCommand(reason, description);
    }
}
