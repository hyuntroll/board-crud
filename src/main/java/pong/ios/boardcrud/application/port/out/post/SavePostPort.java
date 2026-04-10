package pong.ios.boardcrud.application.port.out.post;

import pong.ios.boardcrud.domain.post.Post;

public interface SavePostPort {
    Post save(Post post);
}
