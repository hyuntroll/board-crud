package pong.ios.boardcrud.global.infra.security.jwt.error;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.error.StatusCode;

@Getter
@RequiredArgsConstructor
public enum JwtStatusCode implements StatusCode {
    EXPIRED_TOKEN("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus httpStatus;
}
