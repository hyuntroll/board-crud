package pong.ios.boardcrud.adapter.in.rest.report;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.report.docs.PostReportControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.report.dto.request.CreatePostReportRequest;
import pong.ios.boardcrud.adapter.in.rest.report.dto.request.UpdatePostReportStatusRequest;
import pong.ios.boardcrud.adapter.in.rest.report.dto.response.PostReportResponse;
import pong.ios.boardcrud.application.port.in.report.CreatePostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.DeletePostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.GetPostReportUseCase;
import pong.ios.boardcrud.application.port.in.report.UpdatePostReportStatusUseCase;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostReportController implements PostReportControllerDocs {
    private final CreatePostReportUseCase createPostReportUseCase;
    private final GetPostReportUseCase getPostReportUseCase;
    private final UpdatePostReportStatusUseCase updatePostReportStatusUseCase;
    private final DeletePostReportUseCase deletePostReportUseCase;

    @Override
    @PostMapping("/api/v1/posts/{postId}/reports")
    public ResponseEntity<BaseResponse<PostReportResponse>> createReport(@PathVariable Long postId, @Valid @RequestBody CreatePostReportRequest request) {
        return BaseResponse.created(
                "신고가 접수되었습니다.",
                PostReportResponse.from(createPostReportUseCase.createReport(postId, request.toCommand()))
        );
    }

    @Override
    @GetMapping("/api/v1/posts/{postId}/reports")
    public ResponseEntity<BaseResponse<List<PostReportResponse>>> getReportsByPost(@PathVariable Long postId) {
        List<PostReportResponse> reports = getPostReportUseCase.getReportsByPost(postId).stream()
                .map(PostReportResponse::from)
                .toList();
        return BaseResponse.ok(reports);
    }

    @Override
    @PatchMapping("/api/v1/reports/{reportId}/status")
    public ResponseEntity<BaseResponse<Void>> updateStatus(@PathVariable Long reportId, @Valid @RequestBody UpdatePostReportStatusRequest request) {
        updatePostReportStatusUseCase.updateStatus(reportId, request.status());
        return BaseResponse.ok("신고 상태가 변경되었습니다.");
    }

    @Override
    @DeleteMapping("/api/v1/reports/{reportId}")
    public ResponseEntity<BaseResponse<Void>> deleteReport(@PathVariable Long reportId) {
        deletePostReportUseCase.deleteReport(reportId);
        return BaseResponse.ok("신고 내역이 삭제되었습니다.");
    }
}
