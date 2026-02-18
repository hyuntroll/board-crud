package pong.ios.boardcrud.domain.post;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDraft {
    private Long id;
    private User user;
    private Board board;
    private String title;
    private String content;
    private LocalDateTime savedAt;
    private LocalDateTime createdAt;

    public void updateDraft(Board board, String title, String content, LocalDateTime savedAt) {
        this.board = board;
        this.title = title;
        this.content = content;
        this.savedAt = savedAt;
    }
}
