package pong.ios.boardcrud.domain.post;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDraft {
    private final Long id;
    private final User user;
    private final Board board;
    private final String title;
    private final String content;
    private final LocalDateTime savedAt;
    private final LocalDateTime createdAt;
}
