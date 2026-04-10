package pong.ios.boardcrud.application.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.report.CreatePostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.DeletePostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.GetPostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.UpdatePostReportStatusUseCase;
import pong.ios.boardcrud.application.port.in.report.dto.CreatePostReportCommand;
import pong.ios.boardcrud.application.port.in.report.dto.PostReportResult;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.application.port.out.report.DeletePostReportPort;
import pong.ios.boardcrud.application.port.out.report.LoadPostReportPort;
import pong.ios.boardcrud.application.port.out.report.SavePostReportPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostErrorStatusCode;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.domain.report.PostReport;
import pong.ios.boardcrud.domain.report.PostReportStatus;
import pong.ios.boardcrud.domain.report.PostReportStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReportService implements
        CreatePostReportUseCase,
        GetPostReportUseCase,
        UpdatePostReportStatusUseCase,
        DeletePostReportUseCase {
    private static final long AUTO_BLIND_THRESHOLD = 5;

    private final LoadPostReportPort loadPostReportPort;
    private final SavePostReportPort savePostReportPort;
    private final DeletePostReportPort deletePostReportPort;
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;
    private final LoadUserPort loadUserPort;
    private final SecurityHolder securityHolder;

    @Override
    @Transactional
    public PostReportResult createReport(Long postId, CreatePostReportCommand command) {
        Long reporterId = securityHolder.getCurrentUserId();
        if (loadPostReportPort.existsByPostIdAndReporterId(postId, reporterId)) {
            throw new ApplicationException(PostReportStatusCode.REPORT_ALREADY_EXISTS);
        }

        User reporter = loadUserPort.findById(reporterId)
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));

        Post post = loadPostPort.findById(postId)
                .orElseThrow(() -> new ApplicationException(PostErrorStatusCode.POST_NOT_FOUND));
        if (post.getStatus() == PostStatus.DELETED) {
            throw new ApplicationException(PostErrorStatusCode.POST_DELETED);
        }

        PostReport saved = savePostReportPort.save(
                PostReport.builder()
                        .post(Post.builder().id(postId).build())
                        .reporter(reporter)
                        .reason(command.reason())
                        .description(command.description())
                        .status(PostReportStatus.RECEIVED)
                        .build()
        );

        long count = loadPostReportPort.countByPostId(postId);
        if (!post.isBlinded() && count >= AUTO_BLIND_THRESHOLD) {
            post.blind(LocalDateTime.now());
            savePostPort.save(post);
        }

        return PostReportResult.from(saved);
    }

    @Override
    public List<PostReportResult> getReportsByPost(Long postId) {
        Post post = loadPostPort.findById(postId)
                .orElseThrow(() -> new ApplicationException(PostErrorStatusCode.POST_NOT_FOUND));
        if (post.getStatus() == PostStatus.DELETED) {
            throw new ApplicationException(PostErrorStatusCode.POST_DELETED);
        }

        return loadPostReportPort.findAllByPostId(postId).stream()
                .map(PostReportResult::from)
                .toList();
    }

    @Override
    @Transactional
    public void updateStatus(Long reportId, PostReportStatus status) {
        PostReport report = loadPostReportPort.findById(reportId)
                .orElseThrow(() -> new ApplicationException(PostReportStatusCode.REPORT_NOT_FOUND));
        report.updateStatus(status, LocalDateTime.now());
        savePostReportPort.save(report);
    }

    @Override
    @Transactional
    public void deleteReport(Long reportId) {
        loadPostReportPort.findById(reportId)
                .orElseThrow(() -> new ApplicationException(PostReportStatusCode.REPORT_NOT_FOUND));
        deletePostReportPort.deleteById(reportId);
    }
}
