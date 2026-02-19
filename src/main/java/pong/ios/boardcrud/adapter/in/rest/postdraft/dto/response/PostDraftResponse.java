package pong.ios.boardcrud.adapter.in.rest.postdraft.dto.response;

import pong.ios.boardcrud.application.port.in.postdraft.dto.PostDraftResult;

import java.time.LocalDateTime;

public record PostDraftResponse(
        Long id,
        Long userId,
        Long boardId,
        String title,
        String content,
        LocalDateTime savedAt,
        LocalDateTime createdAt
) {
    public static PostDraftResponse from(PostDraftResult result) {
        return new PostDraftResponse(
                result.id(),
                result.userId(),
                result.boardId(),
                result.title(),
                result.content(),
                result.savedAt(),
                result.createdAt()
        );
    }
}
