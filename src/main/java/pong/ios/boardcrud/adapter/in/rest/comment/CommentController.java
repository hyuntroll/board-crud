package pong.ios.boardcrud.adapter.in.rest.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.comment.docs.CommentControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.CreateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.UpdateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.response.CommentResponse;
import pong.ios.boardcrud.application.port.in.comment.CreateCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.DeleteCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.GetCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.UpdateCommentUseCase;
import pong.ios.boardcrud.application.port.in.comment.dto.CommentResult;
import pong.ios.boardcrud.application.port.in.like.LikeCommentUseCase;
import pong.ios.boardcrud.application.port.in.like.UnlikeCommentUseCase;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {
    private final CreateCommentUseCase createCommentUseCase;
    private final GetCommentUseCase getCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final LikeCommentUseCase likeCommentUseCase;
    private final UnlikeCommentUseCase unlikeCommentUseCase;

    @Override
    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<BaseResponse<CommentResponse>> createComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request) {
        return BaseResponse.created(
                "댓글이 작성되었습니다.",
                CommentResponse.from(createCommentUseCase.createComment(request.toCommand(postId)))
        );
    }

    @Override
    @GetMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<BaseResponse<PageResult<CommentResponse>>> getCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PageQuery query = new PageQuery(page, size, sortBy, direction);
        PageResult<CommentResult> result = getCommentUseCase.getCommentsByPost(postId, query);
        return BaseResponse.ok(result.map(CommentResponse::from));
    }

    @Override
    @PutMapping("/api/v1/comments/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request) {
        return BaseResponse.ok(
                "댓글이 수정되었습니다.",
                CommentResponse.from(updateCommentUseCase.updateComment(request.toCommand(commentId)))
        );
    }

    @Override
    @DeleteMapping("/api/v1/comments/{commentId}")
    public ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable Long commentId) {
        deleteCommentUseCase.deleteComment(commentId);
        return BaseResponse.ok("댓글이 삭제되었습니다.");
    }

    @Override
    @PostMapping("/api/v1/comments/{commentId}/likes")
    public ResponseEntity<BaseResponse<Void>> likeComment(@PathVariable Long commentId) {
        likeCommentUseCase.likeComment(commentId);
        return BaseResponse.ok("댓글 좋아요를 눌렀습니다.");
    }

    @Override
    @DeleteMapping("/api/v1/comments/{commentId}/likes")
    public ResponseEntity<BaseResponse<Void>> unlikeComment(@PathVariable Long commentId) {
        unlikeCommentUseCase.unlikeComment(commentId);
        return BaseResponse.ok("댓글 좋아요를 취소했습니다.");
    }
}
