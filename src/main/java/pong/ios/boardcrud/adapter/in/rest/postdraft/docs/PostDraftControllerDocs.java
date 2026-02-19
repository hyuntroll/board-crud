package pong.ios.boardcrud.adapter.in.rest.postdraft.docs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.request.SavePostDraftRequest;
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.response.PostDraftResponse;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageResult;

public interface PostDraftControllerDocs {
    ResponseEntity<BaseResponse<PostDraftResponse>> saveDraft(@Valid @RequestBody SavePostDraftRequest request);

    ResponseEntity<BaseResponse<PostDraftResponse>> getDraft(@PathVariable Long draftId);

    ResponseEntity<BaseResponse<PageResult<PostDraftResponse>>> getMyDrafts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "savedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Long boardId
    );

    ResponseEntity<BaseResponse<Void>> deleteDraft(@PathVariable Long draftId);
}
