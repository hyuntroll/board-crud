package pong.ios.boardcrud.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER");

    private final String key;
}
