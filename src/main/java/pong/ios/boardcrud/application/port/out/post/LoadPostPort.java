package pong.ios.boardcrud.application.port.out.post;

import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;

import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> findById(Long postId);

    PageResult<Post> findAll(PageQuery query);

    PageResult<Post> findAllByBoardId(Long boardId, PageQuery query);
}
