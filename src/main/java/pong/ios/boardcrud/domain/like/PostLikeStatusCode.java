package pong.ios.boardcrud.domain.like;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum PostLikeStatusCode implements StatusCode {
    POST_LIKE_ALREADY_EXISTS("이미 게시글 좋아요를 눌렀습니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;
}
