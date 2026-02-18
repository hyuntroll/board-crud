package pong.ios.boardcrud.domain.post;

import lombok.Builder;
import lombok.Getter;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Post {
    private final Long id;
    private final User user;
    private final Board board;
    private final String title;
    private final String content;
    private final String category;
    private final List<String> tags;
    private final PostStatus status;
    private final boolean isPinned;
    private final LocalDateTime pinnedAt;
    private final User pinnedBy;
    private final int viewCount;
    private final int likeCount;
    private final int commentCount;
    private final LocalDateTime deletedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
