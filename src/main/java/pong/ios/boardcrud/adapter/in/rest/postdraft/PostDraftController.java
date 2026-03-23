package pong.ios.boardcrud.adapter.in.rest.postdraft;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.postdraft.docs.PostDraftControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.request.SavePostDraftRequest;
import pong.ios.boardcrud.adapter.in.rest.postdraft.dto.response.PostDraftResponse;
import pong.ios.boardcrud.application.port.in.postdraft.DeletePostDraftUseCase;
import pong.ios.boardcrud.application.port.in.postdraft.GetPostDraftUseCase;
import pong.ios.boardcrud.application.port.in.postdraft.SavePostDraftUseCase;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;
import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-drafts")
public class PostDraftController implements PostDraftControllerDocs {
    private final SavePostDraftUseCase savePostDraftUseCase;
    private final GetPostDraftUseCase getPostDraftUseCase;
    private final DeletePostDraftUseCase deletePostDraftUseCase;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<PostDraftResponse>> saveDraft(@Valid @RequestBody SavePostDraftRequest request) {
        return BaseResponse.ok(
                "임시 저장되었습니다.",
                PostDraftResponse.from(savePostDraftUseCase.saveDraft(request.toCommand()))
        );
    }

    @Override
    @GetMapping("/{draftId}")
    public ResponseEntity<BaseResponse<PostDraftResponse>> getDraft(@PathVariable Long draftId) {
        return BaseResponse.ok(
                PostDraftResponse.from(getPostDraftUseCase.getDraft(draftId))
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<PageResult<PostDraftResponse>>> getMyDrafts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "savedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Long boardId
    ) {
        PageQuery query = new PageQuery(page, size, sortBy, direction);
        PageResult<PostDraftResult> result = getPostDraftUseCase.getMyDrafts(boardId, query);
        return BaseResponse.ok(
                result.map(PostDraftResponse::from)
        );
    }

    @Override
    @DeleteMapping("/{draftId}")
    public ResponseEntity<BaseResponse<Void>> deleteDraft(@PathVariable Long draftId) {
        deletePostDraftUseCase.deleteDraft(draftId);
        return BaseResponse.ok("임시 저장글이 삭제되었습니다.");
    }
}
