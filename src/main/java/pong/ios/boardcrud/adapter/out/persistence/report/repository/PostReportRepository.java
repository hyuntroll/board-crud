package pong.ios.boardcrud.adapter.out.persistence.report.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.report.entity.PostReportEntity;

import java.util.List;
import java.util.Optional;

public interface PostReportRepository extends JpaRepository<PostReportEntity, Long> {
    boolean existsByPost_IdAndReporter_Id(Long postId, Long reporterId);

    long countByPost_Id(Long postId);

    @Override
    @EntityGraph(attributePaths = {"post", "reporter"})
    Optional<PostReportEntity> findById(Long id);

    @EntityGraph(attributePaths = {"post", "reporter"})
    List<PostReportEntity> findAllByPost_IdOrderByCreatedAtDesc(Long postId);
}
