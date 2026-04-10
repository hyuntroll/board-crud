package pong.ios.boardcrud.application.port.in.report;

import pong.ios.boardcrud.application.port.in.report.dto.PostReportResult;

import java.util.List;

public interface GetPostReportUseCase {
    List<PostReportResult> getReportsByPost(Long postId);
}
