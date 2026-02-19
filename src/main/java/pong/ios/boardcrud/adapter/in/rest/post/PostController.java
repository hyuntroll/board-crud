package pong.ios.boardcrud.adapter.in.rest.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.post.docs.PostControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.post.dto.request.CreatePostRequest;
import pong.ios.boardcrud.adapter.in.rest.post.dto.request.UpdatePostRequest;
import pong.ios.boardcrud.adapter.in.rest.post.dto.response.PostResponse;
import pong.ios.boardcrud.adapter.in.rest.post.dto.response.PostSummary;
import pong.ios.boardcrud.application.port.in.post.CreatePostUseCase;
import pong.ios.boardcrud.application.port.in.post.DeletePostUseCase;
import pong.ios.boardcrud.application.port.in.post.GetPostUseCase;
import pong.ios.boardcrud.application.port.in.post.UpdatePostUseCase;
import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController implements PostControllerDocs {
    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @Override
    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request) {
        return BaseResponse.created(
                "게시글이 작성되었습니다.",
                PostResponse.from(createPostUseCase.createPost(request.toCommand()))
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<PageResult<PostSummary>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Long boardId
    ) {
        PageQuery query = new PageQuery(page, size, sortBy, direction);
        PageResult<PostResult> result = (boardId == null
                ? getPostUseCase.getPosts(query)
                : getPostUseCase.getPostsByBoard(boardId, query));

        return BaseResponse.ok(result.map(PostSummary::from));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostDetail(@PathVariable Long postId) {
        return BaseResponse.ok(PostResponse.from(getPostUseCase.getPostDetail(postId)));
    }

    @Override
    @PutMapping("/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        return BaseResponse.ok(
                "게시글이 수정되었습니다.",
                PostResponse.from(updatePostUseCase.updatePost(request.toCommand(postId)))
        );
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId) {
        deletePostUseCase.deletePost(postId);
        return BaseResponse.ok("게시글이 삭제되었습니다.");
    }
}
