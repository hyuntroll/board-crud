package pong.ios.boardcrud.adapter.in.rest.post.dto.response;

import pong.ios.boardcrud.application.port.in.post.dto.PostResult;

import java.time.LocalDateTime;

public record PostSummary(
        Long id,
        Long boardId,
        String title,
        String category,
        String writerNickname,
        boolean isPinned,
        int viewCount,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostSummary from(PostResult result) {
        return new PostSummary(
                result.id(),
                result.boardId(),
                result.title(),
                result.category(),
                result.writerNickname(),
                result.isPinned(),
                result.viewCount(),
                result.likeCount(),
                result.commentCount(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
