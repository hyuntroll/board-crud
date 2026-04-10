package pong.ios.boardcrud.adapter.in.rest.board.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.CreateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.GrantBoardManagerRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.UpdateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.response.BoardResponse;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;

@Tag(name = "Board", description = "게시판 API")
public interface BoardControllerDocs {
    @Operation(summary = "게시판 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Long>> createBoard(@Valid @RequestBody CreateBoardRequest request);

    @Operation(summary = "게시판 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 없음")
    })
    ResponseEntity<BaseResponse<BoardResponse>> getBoard(@PathVariable Long boardId);

    @Operation(summary = "게시판 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시판 관리자 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시판 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> updateBoard(@PathVariable Long boardId, @Valid @RequestBody UpdateBoardRequest request);

    @Operation(summary = "게시판 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시판 관리자 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시판 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> deleteBoard(@PathVariable Long boardId);

    @Operation(summary = "게시판 관리자 권한 부여")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "권한 부여 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시판 관리자 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시판/유저 없음"),
            @ApiResponse(responseCode = "409", description = "이미 권한이 존재")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> grantBoardManager(@PathVariable Long boardId, @Valid @RequestBody GrantBoardManagerRequest request);

    @Operation(summary = "게시판 관리자 권한 회수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "권한 회수 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "게시판 관리자 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시판/관리자 권한 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<Void>> depriveBoardManager(@PathVariable Long boardId, @PathVariable Long userId);
}
