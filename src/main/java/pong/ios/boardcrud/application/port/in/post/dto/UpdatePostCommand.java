package pong.ios.boardcrud.application.port.in.post.dto;

import java.util.List;

public record UpdatePostCommand(
        Long postId,
        String title,
        String content,
        String category,
        List<String> tags
) {
}
