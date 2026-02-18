package pong.ios.boardcrud.domain.board;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardManager {
    private final Long id;
    private final Board board;
    private final User user;
    private final LocalDateTime grantedAt;
    private final User grantedBy;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
