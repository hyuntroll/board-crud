package pong.ios.boardcrud.adapter.in.rest.postdraft.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.postdraft.dto.SavePostDraftCommand;

public record SavePostDraftRequest(
        Long draftId,

        @NotNull(message = "게시판 ID는 필수입니다.")
        Long boardId,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
    public SavePostDraftCommand toCommand() {
        return new SavePostDraftCommand(draftId, boardId, title, content);
    }
}
