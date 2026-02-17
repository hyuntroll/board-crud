package pong.ios.boardcrud.global.exception;

import org.springframework.http.HttpStatus;

public interface StatusCode {
    String getMessage();
    HttpStatus getHttpStatus();
}
