package pong.ios.boardcrud.adapter.in.rest.post.dto.response;

import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.domain.post.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
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
    public static PostResponse from(PostResult result) {
        return new PostResponse(
                result.id(),
                result.userId(),
                result.writerNickname(),
                result.writerProfile(),
                result.boardId(),
                result.title(),
                result.content(),
                result.category(),
                result.tags(),
                result.status(),
                result.viewCount(),
                result.likeCount(),
                result.commentCount(),
                result.deletedAt(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
