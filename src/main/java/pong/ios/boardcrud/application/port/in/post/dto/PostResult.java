package pong.ios.boardcrud.application.port.in.post.dto;

import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PostResult(
        Long id,
        Long userId,
        String writerNickname,
        String writerProfile,
        Long boardId,
        String title,
        String content,
        String category,
        List<String> tags,
        PostStatus status,
        int viewCount,
        int likeCount,
        int commentCount,
        LocalDateTime deletedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResult from(Post post) {
        return new PostResult(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getNickname(),
                post.getUser().getProfile(),
                post.getBoard().getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getTags(),
                post.getStatus(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getDeletedAt(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
