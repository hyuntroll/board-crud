package pong.ios.boardcrud.adapter.in.rest.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import pong.ios.boardcrud.application.port.in.post.dto.UpdatePostCommand;

import java.util.List;

public record UpdatePostRequest(
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "카테고리는 필수입니다.")
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다.")
        String category,

        List<@NotBlank(message = "태그는 공백일 수 없습니다.") String> tags
) {
    public UpdatePostCommand toCommand(Long postId) {
        return new UpdatePostCommand(postId, title, content, category, tags);
    }
}
