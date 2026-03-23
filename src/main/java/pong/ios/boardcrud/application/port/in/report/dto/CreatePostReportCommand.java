package pong.ios.boardcrud.application.port.in.report.dto;

import pong.ios.boardcrud.domain.report.PostReportReason;

public record CreatePostReportCommand(
        PostReportReason reason,
        String description
) {
}
