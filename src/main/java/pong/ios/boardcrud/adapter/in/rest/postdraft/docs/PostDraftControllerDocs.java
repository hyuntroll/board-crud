package pong.ios.boardcrud.adapter.in.rest.postdraft.docs;

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
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.request.SavePostDraftRequest;
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.response.PostDraftResponse;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageResult;

@Tag(name = "PostDraft", description = "게시글 임시저장 API")
@SecurityRequirement(name = "bearerAuth")
public interface PostDraftControllerDocs {
    @Operation(summary = "임시저장 생성/수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "임시저장 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "유저/게시판/임시글 없음")
    })
    ResponseEntity<BaseResponse<PostDraftResponse>> saveDraft(@Valid @RequestBody SavePostDraftRequest request);

    @Operation(summary = "임시저장 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "본인 임시글 아님"),
            @ApiResponse(responseCode = "404", description = "임시글 없음")
    })
    ResponseEntity<BaseResponse<PostDraftResponse>> getDraft(@PathVariable Long draftId);

    @Operation(summary = "내 임시저장 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    ResponseEntity<BaseResponse<PageResult<PostDraftResponse>>> getMyDrafts(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "정렬 필드") @RequestParam(defaultValue = "savedAt") String sortBy,
            @Parameter(description = "정렬 방향") @RequestParam(defaultValue = "desc") String direction,
            @Parameter(description = "게시판 ID") @RequestParam(required = false) Long boardId
    );

    @Operation(summary = "임시저장 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "본인 임시글 아님"),
            @ApiResponse(responseCode = "404", description = "임시글 없음")
    })
    ResponseEntity<BaseResponse<Void>> deleteDraft(@PathVariable Long draftId);
}
