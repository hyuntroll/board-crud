package pong.ios.boardcrud.application.port.in.postdraft.dto;

public record SavePostDraftCommand(
        Long draftId,
        Long boardId,
        String title,
        String content
) {
}
