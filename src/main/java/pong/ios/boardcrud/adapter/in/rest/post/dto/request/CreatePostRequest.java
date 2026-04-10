package pong.ios.boardcrud.adapter.in.rest.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.post.dto.CreatePostCommand;

import java.util.List;

@Schema(description = "게시글 작성 요청")
public record CreatePostRequest(
        @Schema(description = "게시판 ID", example = "1")
        @NotNull(message = "게시판 ID는 필수입니다.")
        Long boardId,

        @Schema(description = "제목", example = "안녕하세요")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        String title,

        @Schema(description = "내용", example = "첫 글입니다.")
        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @Schema(description = "카테고리", example = "자유")
        @NotBlank(message = "카테고리는 필수입니다.")
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
        String category,

        @Schema(description = "태그 목록")
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
