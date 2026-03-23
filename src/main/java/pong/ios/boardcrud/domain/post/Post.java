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
    private boolean isPublic;
    private boolean isCommentAllowed;
    private boolean isBlinded;
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

    public void changeVisibility(boolean isPublic, LocalDateTime updatedAt) {
        this.isPublic = isPublic;
        this.updatedAt = updatedAt;
    }

    public void changeCommentPolicy(boolean isCommentAllowed, LocalDateTime updatedAt) {
        this.isCommentAllowed = isCommentAllowed;
        this.updatedAt = updatedAt;
    }

    public void blind(LocalDateTime updatedAt) {
        this.isPublic = false;
        this.isBlinded = true;
        this.updatedAt = updatedAt;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }

    public void increaseCommentCount() {
        this.commentCount += 1;
    }

    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount -= 1;
        }
    }

    public void resetCommentCount() {
        this.commentCount = 0;
    }

    public void updateEditedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
