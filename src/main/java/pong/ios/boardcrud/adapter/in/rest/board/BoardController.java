package pong.ios.boardcrud.adapter.in.rest.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.board.docs.BoardControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.CreateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.GrantBoardManagerRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.UpdateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.response.BoardResponse;
import pong.ios.boardcrud.adapter.in.rest.common.ApiResponse;
import pong.ios.boardcrud.application.port.in.board.*;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController implements BoardControllerDocs {
    private final CreateBoardUseCase createBoardUseCase;
    private final GetBoardUseCase getBoardUseCase;
    private final UpdateBoardUseCase updateBoardUseCase;
    private final DeleteBoardUseCase deleteBoardUseCase;
    private final GrantBoardManagerUseCase grantBoardManagerUseCase;
    private final DepriveBoardManagerUseCase depriveBoardManagerUseCase;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<Long>> createBoard(@Valid @RequestBody CreateBoardRequest request) {
        Long boardId = createBoardUseCase.createBoard(request.name(), request.type(), request.description());
        return ApiResponse.created("게시판이 생성되었습니다.", boardId);
    }

    @Override
    @GetMapping("/{boardId}")
    public ResponseEntity<BaseResponse<BoardResponse>> getBoard(@PathVariable Long boardId) {
        return ApiResponse.ok(getBoardUseCase.getBoard(boardId));
    }

    @Override
    @PutMapping("/{boardId}")
    public ResponseEntity<BaseResponse<Void>> updateBoard(@PathVariable Long boardId, @Valid @RequestBody UpdateBoardRequest request) {
        updateBoardUseCase.updateBoard(boardId, request.name(), request.type(), request.description());
        return ApiResponse.ok("게시판이 수정되었습니다.");
    }

    @Override
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BaseResponse<Void>> deleteBoard(@PathVariable Long boardId) {
        deleteBoardUseCase.deleteBoard(boardId);
        return ApiResponse.ok("게시판이 삭제되었습니다.");
    }

    @Override
    @PostMapping("/{boardId}/managers")
    public ResponseEntity<BaseResponse<Void>> grantBoardManager(@PathVariable Long boardId, @Valid @RequestBody GrantBoardManagerRequest request) {
        grantBoardManagerUseCase.give(boardId, request.userId());
        return ApiResponse.ok("게시판 관리자 권한을 부여했습니다.");
    }

    @Override
    @DeleteMapping("/{boardId}/managers/{userId}")
    public ResponseEntity<BaseResponse<Void>> depriveBoardManager(@PathVariable Long boardId, @PathVariable Long userId) {
        depriveBoardManagerUseCase.deprive(boardId, userId);
        return ApiResponse.ok("게시판 관리자 권한을 회수했습니다.");
    }
}
