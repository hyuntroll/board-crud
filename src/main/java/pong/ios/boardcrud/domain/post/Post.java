package pong.ios.boardcrud.domain.post;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Post {
    private Long id;
    private User user;
    private Board board;
    private String title;
    private String content;
    private String category;
    private List<String> tags;
    private PostStatus status;
    private boolean isPinned;
    private LocalDateTime pinnedAt;
    private User pinnedBy;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void pin(User user) {
        pinnedBy = user;
        pinnedAt = LocalDateTime.now();
        isPinned = true;
    }

    public void unpin() {
        pinnedBy = null;
        pinnedAt = null;
        isPinned = false;
    }

    public void updateEditableFields(String title, String content, String category, List<String> tags, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags == null ? List.of() : new ArrayList<>(tags);
        this.updatedAt = updatedAt;
    }

    public void softDelete(LocalDateTime deletedAt, LocalDateTime updatedAt) {
        this.status = PostStatus.DELETED;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }
}
