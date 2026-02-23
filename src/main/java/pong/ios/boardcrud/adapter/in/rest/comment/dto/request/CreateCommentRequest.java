package pong.ios.boardcrud.adapter.in.rest.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import pong.ios.boardcrud.application.port.in.comment.dto.CreateCommentCommand;

@Schema(description = "댓글 작성 요청")
public record CreateCommentRequest(
        @Schema(description = "부모 댓글 ID(현재 미사용, root만 허용)", example = "null", nullable = true)
        Long parentId,

        @Schema(description = "댓글 내용", example = "좋은 글 감사합니다.")
        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {
    public CreateCommentCommand toCommand(Long postId) {
        return new CreateCommentCommand(postId, parentId, content);
    }
}
