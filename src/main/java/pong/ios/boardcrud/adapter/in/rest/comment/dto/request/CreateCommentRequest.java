package pong.ios.boardcrud.adapter.in.rest.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import pong.ios.boardcrud.application.port.in.comment.dto.CreateCommentCommand;

public record CreateCommentRequest(
        Long parentId,

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
    public CreateCommentCommand toCommand(Long postId) {
        return new CreateCommentCommand(postId, parentId, content);
    }
}
