package pong.ios.boardcrud.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String password;
    private final UserRoleType role;
    private final String email;
    private final LocalDate birthDate;
    private final String profile;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
