package pong.ios.boardcrud.domain.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum CommentStatusCode implements StatusCode {
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    COMMENT_FORBIDDEN("해당 댓글에 접근할 수 없습니다.", HttpStatus.FORBIDDEN),
    COMMENT_DELETED("삭제된 댓글입니다.", HttpStatus.CONFLICT),
    COMMENT_PARENT_NOT_FOUND("부모 댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    COMMENT_PARENT_MISMATCH("잘못된 부모 댓글입니다.", HttpStatus.BAD_REQUEST),
    COMMENT_LIKE_ALREADY_EXISTS("이미 댓글 좋아요를 눌렀습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
