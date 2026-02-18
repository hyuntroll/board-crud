package pong.ios.boardcrud.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthStatusCode implements StatusCode {
    INVALID_CREDENTIALS("이메일/아이디 또는 비밀번호가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCK("계정이 일시적으로 잠겼습니다.", HttpStatus.LOCKED),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
