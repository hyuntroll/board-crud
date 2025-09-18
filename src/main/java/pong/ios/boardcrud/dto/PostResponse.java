package pong.ios.boardcrud.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private UserResponse writer;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = new UserResponse(post.getWriter());
    }
}
