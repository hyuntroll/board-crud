package pong.ios.boardcrud.application.port.in.report;

import pong.ios.boardcrud.application.port.in.report.dto.CreatePostReportCommand;
import pong.ios.boardcrud.application.port.in.report.dto.PostReportResult;

public interface CreatePostReportUseCase {
    PostReportResult createReport(Long postId, CreatePostReportCommand command);
}
