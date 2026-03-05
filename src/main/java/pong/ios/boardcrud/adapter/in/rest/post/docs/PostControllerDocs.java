package pong.ios.boardcrud.adapter.in.rest.post.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pong.ios.boardcrud.adapter.in.rest.post.dto.request.CreatePostRequest;
import pong.ios.boardcrud.adapter.in.rest.post.dto.request.UpdatePostRequest;
import pong.ios.boardcrud.adapter.in.rest.post.dto.response.PostResponse;
import pong.ios.boardcrud.adapter.in.rest.post.dto.response.PostSummary;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageResult;

@Tag(name = "Post", description = "게시글 API")
public interface PostControllerDocs {
    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시판/유저 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request);

    @Operation(summary = "게시글 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "요청 파라미터 오류")
    })
    ResponseEntity<BaseResponse<PageResult<PostSummary>>> getPosts(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "정렬 필드") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "정렬 방향") @RequestParam(defaultValue = "desc") String direction,
            @Parameter(description = "게시판 ID") @RequestParam(required = false) Long boardId
    );

    @Operation(summary = "게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    ResponseEntity<BaseResponse<PostResponse>> getPostDetail(@PathVariable Long postId);

    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "작성자 아님"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request);

    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "이미 삭제됨")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId);

    @Operation(summary = "게시글 상단 고정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상단 고정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> pinPost(@PathVariable Long postId);

    @Operation(summary = "게시글 상단 고정 해제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상단 고정 해제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> unpinPost(@PathVariable Long postId);

    @Operation(summary = "게시글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> likePost(@PathVariable Long postId);

    @Operation(summary = "게시글 좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
            @ApiResponse(responseCode = "409", description = "삭제된 게시글")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> unlikePost(@PathVariable Long postId);
}
