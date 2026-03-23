package pong.ios.boardcrud.application.port.out.report;

import pong.ios.boardcrud.domain.report.PostReport;

public interface SavePostReportPort {
    PostReport save(PostReport report);
}
