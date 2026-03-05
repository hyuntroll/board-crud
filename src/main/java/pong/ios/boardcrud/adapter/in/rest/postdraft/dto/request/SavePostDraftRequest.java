package pong.ios.boardcrud.adapter.in.rest.postdraft.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.postdraft.dto.SavePostDraftCommand;

@Schema(description = "임시저장 요청")
public record SavePostDraftRequest(
        @Schema(description = "임시저장 ID(수정 시 사용)", example = "1")
        Long draftId,

        @Schema(description = "게시판 ID", example = "1")
        @NotNull(message = "게시판 ID는 필수입니다.")
        Long boardId,

        @Schema(description = "제목", example = "작성중인 글")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        String title,

        @Schema(description = "내용", example = "초안 내용")
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
    public SavePostDraftCommand toCommand() {
        return new SavePostDraftCommand(draftId, boardId, title, content);
    }
}
