package pong.ios.boardcrud.application.port.in.post;

import pong.ios.boardcrud.application.port.in.post.dto.PostResult;

import java.util.List;

public interface GetPostUseCase {
    List<PostResult> getPosts();

    List<PostResult> getPostsByBoard(Long boardId);

    PostResult getPostDetail(Long postId);
}
