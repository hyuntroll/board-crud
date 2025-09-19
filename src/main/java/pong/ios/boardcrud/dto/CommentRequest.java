package pong.ios.boardcrud.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CommentRequest {

    private Long id;

    private String content;

    public CommentRequest(String content) { this.content = content; }

    public CommentRequest(Long id, String content) {

        this.id = id;

        this.content = content;

    }

}
