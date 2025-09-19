package pong.ios.boardcrud.dto;


import lombok.Getter;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.post.Post;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private List<CommentResponse> comments;

    private int likes;

    public PostResponse(Post post, int likes) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter().getUsername();
        this.likes = likes;

        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());

    }
}
