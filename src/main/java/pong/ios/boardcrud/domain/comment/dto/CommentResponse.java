package pong.ios.boardcrud.dto.comment;


import lombok.Getter;
import pong.ios.boardcrud.domain_1.entity.comment.Comment;

@Getter
public class CommentResponse {

    private Long id;

    private String content;

    private String commenter;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commenter = comment.getCommenter().getUsername();
    }
}
