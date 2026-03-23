package pong.ios.boardcrud.adapter.in.rest.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 댓글 허용 설정 변경 요청")
public record UpdatePostCommentPolicyRequest(
        @Schema(description = "댓글 허용 여부", example = "true")
        @NotNull(message = "댓글 허용 여부는 필수입니다.")
        Boolean isCommentAllowed
) {
}
