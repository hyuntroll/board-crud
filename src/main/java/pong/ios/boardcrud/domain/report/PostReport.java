package pong.ios.boardcrud.domain.report;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostReport {
    private Long id;
    private Post post;
    private User reporter;
    private PostReportReason reason;
    private String description;
    private PostReportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateStatus(PostReportStatus status, LocalDateTime updatedAt) {
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
