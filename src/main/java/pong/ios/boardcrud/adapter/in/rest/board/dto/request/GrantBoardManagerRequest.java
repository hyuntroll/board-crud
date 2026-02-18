package pong.ios.boardcrud.adapter.in.rest.board.dto.request;

import jakarta.validation.constraints.NotNull;

public record GrantBoardManagerRequest(
        @NotNull(message = "권한을 부여할 사용자 ID는 필수입니다.")
        Long userId
) {
}
