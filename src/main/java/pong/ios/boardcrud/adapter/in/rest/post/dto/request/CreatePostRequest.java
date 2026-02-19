package pong.ios.boardcrud.adapter.in.rest.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.post.dto.CreatePostCommand;

import java.util.List;

public record CreatePostRequest(
        @NotNull(message = "게시판 ID는 필수입니다.")
        Long boardId,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "카테고리는 필수입니다.")
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
        String category,

        @Size(max = 10, message = "태그는 최대 10개까지 입력할 수 있습니다.")
        List<
                @NotBlank(message = "태그는 공백일 수 없습니다.")
                @Size(max = 30, message = "태그는 30자 이하여야 합니다.")
                String> tags
) {
    public CreatePostCommand toCommand() {
        return new CreatePostCommand(boardId, title, content, category, tags);
    }
}
