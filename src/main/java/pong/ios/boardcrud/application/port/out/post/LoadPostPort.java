package pong.ios.boardcrud.application.port.out.post;

import pong.ios.boardcrud.domain.post.Post;

import java.util.List;
import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> findById(Long postId);

    List<Post> findAll();

    List<Post> findAllByBoardId(Long boardId);
}
