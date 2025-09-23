package pong.ios.boardcrud.dto.join;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    private String username;

    private String password;

    private String email;
}
