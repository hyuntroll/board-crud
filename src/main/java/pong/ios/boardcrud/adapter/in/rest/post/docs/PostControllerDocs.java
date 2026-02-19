package pong.ios.boardcrud.adapter.in.rest.post.docs;

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

import java.util.List;

public interface PostControllerDocs {
    ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request);

    ResponseEntity<BaseResponse<List<PostSummary>>> getPosts(@RequestParam(required = false) Long boardId);

    ResponseEntity<BaseResponse<PostResponse>> getPostDetail(@PathVariable Long postId);

    ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request);

    ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId);
}
