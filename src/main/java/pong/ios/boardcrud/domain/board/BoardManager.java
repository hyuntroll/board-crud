package pong.ios.boardcrud.domain.board;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardManager {
    private Long id;
    private Board board;
    private User user;
    private LocalDateTime grantedAt;
    private User grantedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeGrantInfo(User grantedBy, LocalDateTime grantedAt) {
        this.grantedBy = grantedBy;
        this.grantedAt = grantedAt;
    }
}
