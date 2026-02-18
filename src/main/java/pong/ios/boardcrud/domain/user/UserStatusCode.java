package pong.ios.boardcrud.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum UserStatusCode implements StatusCode {
    DUPLICATE_EMAIL("이미 사용중인 이메일 입니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATE_USERNAME("이미 사용중인 아이디 입니다.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),;

    private final String message;
    private final HttpStatus httpStatus;
}
