package pong.ios.boardcrud.adapter.in.rest.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import pong.ios.boardcrud.application.port.in.comment.dto.UpdateCommentCommand;

@Schema(description = "댓글 수정 요청")
public record UpdateCommentRequest(
        @Schema(description = "수정할 댓글 내용", example = "수정된 댓글입니다.")
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
    public UpdateCommentCommand toCommand(Long commentId) {
        return new UpdateCommentCommand(commentId, content);
    }
}
