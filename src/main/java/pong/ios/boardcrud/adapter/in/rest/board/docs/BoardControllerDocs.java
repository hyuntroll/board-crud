package pong.ios.boardcrud.adapter.in.rest.board.docs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.CreateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.GrantBoardManagerRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.request.UpdateBoardRequest;
import pong.ios.boardcrud.adapter.in.rest.board.dto.response.BoardResponse;
import pong.ios.boardcrud.global.data.BaseResponse;

public interface BoardControllerDocs {
    ResponseEntity<BaseResponse<Long>> createBoard(@Valid @RequestBody CreateBoardRequest request);

    ResponseEntity<BaseResponse<BoardResponse>> getBoard(@PathVariable Long boardId);

    ResponseEntity<BaseResponse<Void>> updateBoard(@PathVariable Long boardId, @Valid @RequestBody UpdateBoardRequest request);

    ResponseEntity<BaseResponse<Void>> deleteBoard(@PathVariable Long boardId);

    ResponseEntity<BaseResponse<Void>> grantBoardManager(@PathVariable Long boardId, @Valid @RequestBody GrantBoardManagerRequest request);

    ResponseEntity<BaseResponse<Void>> depriveBoardManager(@PathVariable Long boardId, @PathVariable Long userId);
}
