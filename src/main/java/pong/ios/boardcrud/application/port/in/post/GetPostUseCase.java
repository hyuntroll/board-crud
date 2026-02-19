package pong.ios.boardcrud.application.port.in.post;

import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

public interface GetPostUseCase {
    PageResult<PostResult> getPosts(PageQuery query);

    PageResult<PostResult> getPostsByBoard(Long boardId, PageQuery query);

    PostResult getPostDetail(Long postId);
}
