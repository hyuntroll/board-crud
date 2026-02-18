package pong.ios.boardcrud.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum PostDraftStatusCode implements StatusCode {
    POST_DRAFT_NOT_FOUND("임시 저장된 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    POST_DRAFT_FORBIDDEN("해당 임시 저장 게시글에 접근할 수 없습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
