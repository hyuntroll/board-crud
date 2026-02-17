package pong.ios.boardcrud.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonStatusCode implements StatusCode {
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ARGUMENT( "유효하지 않은 인자입니다.", HttpStatus.BAD_REQUEST),
    ENDPOINT_NOT_FOUND("존재하지 않는 엔드포인트입니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("허용되지 않은 메서드입니다", HttpStatus.METHOD_NOT_ALLOWED),
    UNAUTHORIZED("인증이 필요합니다", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("권한이 없습니다", HttpStatus.FORBIDDEN),
    TOO_MANY_REQUEST("너무 많은 요청이 들어왔습니다", HttpStatus.TOO_MANY_REQUESTS),
    ACCESS_DENIED("접근이 거부되었습니다.", HttpStatus.FORBIDDEN);


    private final String message;
    private final HttpStatus httpStatus;
}
