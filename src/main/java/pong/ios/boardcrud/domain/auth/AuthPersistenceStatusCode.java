package pong.ios.boardcrud.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum AuthPersistenceStatusCode implements StatusCode {
    AUTH_TOKEN_PERSISTENCE_ERROR("인증 토큰 저장소 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    LOGIN_ATTEMPT_PERSISTENCE_ERROR("로그인 시도 저장소 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;
}
