package pong.ios.boardcrud.application.port.out.post;

import pong.ios.boardcrud.domain.post.Post;

import java.util.Optional;

public interface LoadPostPort {
    Optional<Post> findById(Long postId);
}
