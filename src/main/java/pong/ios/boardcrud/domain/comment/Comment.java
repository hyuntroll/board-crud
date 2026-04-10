package pong.ios.boardcrud.domain.comment;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class Comment {
    private Long id;
    private Post post;
    private User user;
    private Comment parent;
    private String content;
    private CommentStatus status;
    private int likeCount;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateContent(String content, LocalDateTime updatedAt) {
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public void softDelete(LocalDateTime now) {
        this.status = CommentStatus.DELETED;
        this.content = "삭제된 댓글입니다.";
        this.deletedAt = now;
        this.updatedAt = now;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}
