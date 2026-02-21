package pong.ios.boardcrud.adapter.in.rest.comment.docs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.CreateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.request.UpdateCommentRequest;
import pong.ios.boardcrud.adapter.in.rest.comment.dto.response.CommentResponse;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.data.PageResult;

public interface CommentControllerDocs {
    ResponseEntity<BaseResponse<CommentResponse>> createComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentRequest request);

    ResponseEntity<BaseResponse<PageResult<CommentResponse>>> getCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    );

    ResponseEntity<BaseResponse<CommentResponse>> updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request);

    ResponseEntity<BaseResponse<Void>> deleteComment(@PathVariable Long commentId);

    ResponseEntity<BaseResponse<Void>> likeComment(@PathVariable Long commentId);

    ResponseEntity<BaseResponse<Void>> unlikeComment(@PathVariable Long commentId);
}
