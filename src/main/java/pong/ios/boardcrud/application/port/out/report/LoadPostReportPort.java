package pong.ios.boardcrud.application.port.out.report;

import pong.ios.boardcrud.domain.report.PostReport;

import java.util.List;
import java.util.Optional;

public interface LoadPostReportPort {
    boolean existsByPostIdAndReporterId(Long postId, Long reporterId);

    long countByPostId(Long postId);

    Optional<PostReport> findById(Long reportId);

    List<PostReport> findAllByPostId(Long postId);
}
