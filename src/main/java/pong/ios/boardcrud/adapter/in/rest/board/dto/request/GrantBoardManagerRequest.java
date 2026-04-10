package pong.ios.boardcrud.adapter.in.rest.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시판 관리자 권한 부여 요청")
public record GrantBoardManagerRequest(
        @Schema(description = "권한을 부여할 사용자 ID", example = "2")
        @NotNull(message = "권한을 부여할 사용자 ID는 필수입니다.")
        Long userId
) {
}
