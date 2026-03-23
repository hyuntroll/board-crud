package pong.ios.boardcrud.adapter.in.rest.comment.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.CreateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.UpdateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.response.CommentResponse;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;

import java.util.List;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentControllerDocs {
    @Operation(summary = "댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<CommentResponse>> createComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request);

    @Operation(summary = "루트 댓글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    ResponseEntity<BaseResponse<List<CommentResponse>>> getCommentsByPost(@PathVariable Long postId);

    @Operation(summary = "대댓글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "댓글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 댓글")
    })
    ResponseEntity<BaseResponse<List<CommentResponse>>> getReplies(@PathVariable Long commentId);

    @Operation(summary = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "작성자 아님"),
            @ApiResponse(responseCode = "404", description = "댓글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 댓글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<CommentResponse>> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request);

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 댓글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable Long commentId);

    @Operation(summary = "댓글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 댓글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> likeComment(@PathVariable Long commentId);

    @Operation(summary = "댓글 좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "댓글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 댓글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> unlikeComment(@PathVariable Long commentId);
}
