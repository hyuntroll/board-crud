package pong.ios.boardcrud.application.port.in.comment.dto;

public record UpdateCommentCommand(
        Long commentId,
        String content
) {
}
