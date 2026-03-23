package pong.ios.boardcrud.application.port.in.report;

import pong.ios.boardcrud.domain.report.PostReportStatus;

public interface UpdatePostReportStatusUseCase {
    void updateStatus(Long reportId, PostReportStatus status);
}
