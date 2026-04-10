package pong.ios.boardcrud.application.port.in.report.dto;

import pong.ios.boardcrud.domain.report.PostReport;
import pong.ios.boardcrud.domain.report.PostReportReason;
import pong.ios.boardcrud.domain.report.PostReportStatus;

import java.time.LocalDateTime;

public record PostReportResult(
        Long id,
        Long postId,
        Long reporterId,
        String reporterUsername,
        String reporterNickname,
        PostReportReason reason,
        String description,
        PostReportStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostReportResult from(PostReport report) {
        return new PostReportResult(
                report.getId(),
                report.getPost().getId(),
                report.getReporter().getId(),
                report.getReporter().getUsername(),
                report.getReporter().getNickname(),
                report.getReason(),
                report.getDescription(),
                report.getStatus(),
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }
}
