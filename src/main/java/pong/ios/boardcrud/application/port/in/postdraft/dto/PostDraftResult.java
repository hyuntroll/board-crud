package pong.ios.boardcrud.application.port.in.postdraft.dto;

import pong.ios.boardcrud.domain.post.PostDraft;

import java.time.LocalDateTime;

public record PostDraftResult(
        Long id,
        Long userId,
        Long boardId,
        String title,
        String content,
        LocalDateTime savedAt,
        LocalDateTime createdAt
) {
    public static PostDraftResult from(PostDraft postDraft) {
        return new PostDraftResult(
                postDraft.getId(),
                postDraft.getUser().getId(),
                postDraft.getBoard().getId(),
                postDraft.getTitle(),
                postDraft.getContent(),
                postDraft.getSavedAt(),
                postDraft.getCreatedAt()
        );
    }
}
