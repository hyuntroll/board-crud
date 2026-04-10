package pong.ios.boardcrud.adapter.out.persistence.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.report.mapper.PostReportMapper;
import pong.ios.boardcrud.adapter.out.persistence.report.repository.PostReportRepository;
import pong.ios.boardcrud.application.port.out.report.DeletePostReportPort;
import pong.ios.boardcrud.application.port.out.report.LoadPostReportPort;
import pong.ios.boardcrud.application.port.out.report.SavePostReportPort;
import pong.ios.boardcrud.domain.report.PostReport;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostReportPersistenceAdapter implements LoadPostReportPort, SavePostReportPort, DeletePostReportPort {
    private final PostReportRepository postReportRepository;
    private final PostReportMapper postReportMapper;

    @Override
    public boolean existsByPostIdAndReporterId(Long postId, Long reporterId) {
        return postReportRepository.existsByPost_IdAndReporter_Id(postId, reporterId);
    }

    @Override
    public long countByPostId(Long postId) {
        return postReportRepository.countByPost_Id(postId);
    }

    @Override
    public Optional<PostReport> findById(Long reportId) {
        return postReportRepository.findById(reportId).map(postReportMapper::toDomain);
    }

    @Override
    public List<PostReport> findAllByPostId(Long postId) {
        return postReportRepository.findAllByPost_IdOrderByCreatedAtDesc(postId).stream()
                .map(postReportMapper::toDomain)
                .toList();
    }

    @Override
    public PostReport save(PostReport report) {
        return postReportMapper.toDomain(postReportRepository.save(postReportMapper.toEntity(report)));
    }

    @Override
    public void deleteById(Long reportId) {
        postReportRepository.deleteById(reportId);
    }
}
