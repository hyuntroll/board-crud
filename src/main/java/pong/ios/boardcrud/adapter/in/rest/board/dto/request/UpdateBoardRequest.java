package pong.ios.boardcrud.adapter.in.rest.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시판 수정 요청")
public record UpdateBoardRequest(
        @Schema(description = "게시판 이름", example = "공지사항")
        @NotBlank(message = "게시판 이름은 필수입니다.")
        @Size(max = 100, message = "게시판 이름은 100자 이하여야 합니다.")
        String name,

        @Schema(description = "게시판 타입", example = "NOTICE")
        @NotBlank(message = "게시판 타입은 필수입니다.")
        @Size(max = 50, message = "게시판 타입은 50자 이하여야 합니다.")
        String type,

        @Schema(description = "게시판 설명", example = "공지 전용 게시판")
        @Size(max = 500, message = "설명은 500자 이하여야 합니다.")
        String description
) {
}
