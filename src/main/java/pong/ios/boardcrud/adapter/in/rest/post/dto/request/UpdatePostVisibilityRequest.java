package pong.ios.boardcrud.adapter.in.rest.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 공개 설정 변경 요청")
public record UpdatePostVisibilityRequest(
        @Schema(description = "공개 여부", example = "true")
        @NotNull(message = "공개 여부는 필수입니다.")
        Boolean isPublic
) {
}
