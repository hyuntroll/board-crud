package pong.ios.boardcrud.adapter.in.rest.report.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.report.dto.request.CreatePostReportRequest;
import pong.ios.boardcrud.adapter.in.rest.report.dto.request.UpdatePostReportStatusRequest;
import pong.ios.boardcrud.adapter.in.rest.report.dto.response.PostReportResponse;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;

import java.util.List;

@Tag(name = "Report", description = "게시글 신고 API")
public interface PostReportControllerDocs {
    @Operation(summary = "게시글 신고")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "신고 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "이미 신고함/삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<PostReportResponse>> createReport(@PathVariable Long postId, @Valid @RequestBody CreatePostReportRequest request);

    @Operation(summary = "게시글 신고 목록 조회(관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<List<PostReportResponse>>> getReportsByPost(@PathVariable Long postId);

    @Operation(summary = "신고 처리 상태 변경(관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "신고 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> updateStatus(@PathVariable Long reportId, @Valid @RequestBody UpdatePostReportStatusRequest request);

    @Operation(summary = "신고 삭제(관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "신고 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> deleteReport(@PathVariable Long reportId);
}
