package pong.ios.boardcrud.domain.post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeResponse {

    private Long id;

    private int like;

}
