package pong.ios.boardcrud.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum PostErrorStatusCode implements StatusCode {
    POST_NOT_FOUND("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    POST_FORBIDDEN("해당 게시글에 접근할 수 없습니다.", HttpStatus.FORBIDDEN),
    POST_COMMENT_NOT_ALLOWED("댓글이 허용되지 않는 게시글입니다.", HttpStatus.FORBIDDEN),
    POST_DELETED("삭제된 게시글입니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
