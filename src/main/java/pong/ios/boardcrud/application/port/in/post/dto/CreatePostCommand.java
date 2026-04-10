package pong.ios.boardcrud.application.port.in.post.dto;

import java.util.List;

public record CreatePostCommand(
        Long boardId,
        String title,
        String content,
        String category,
        List<String> tags
) {
}
