package pong.ios.boardcrud.global.security.auth.dto;

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
