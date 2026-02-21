package pong.ios.boardcrud.adapter.in.rest.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import pong.ios.boardcrud.application.port.in.comment.dto.UpdateCommentCommand;

public record UpdateCommentRequest(
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
    public UpdateCommentCommand toCommand(Long commentId) {
        return new UpdateCommentCommand(commentId, content);
    }
}
