package pong.ios.boardcrud.adapter.in.rest.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBoardRequest(
        @NotBlank(message = "게시판 이름은 필수입니다.")
        @Size(max = 100, message = "게시판 이름은 100자 이하여야 합니다.")
        String name,

        @NotBlank(message = "게시판 타입은 필수입니다.")
        @Size(max = 50, message = "게시판 타입은 50자 이하여야 합니다.")
        String type,

        @Size(max = 500, message = "설명은 500자 이하여야 합니다.")
        String description
) {
}
