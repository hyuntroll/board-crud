package pong.ios.boardcrud.application.port.in.comment.dto;

public record CreateCommentCommand(
        Long postId,
        Long parentId,
        String content
) {
}
