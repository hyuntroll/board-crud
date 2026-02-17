package pong.ios.boardcrud.global.error;

import org.springframework.http.HttpStatus;

public interface StatusCode {
    String getMessage();
    HttpStatus getHttpStatus();
}
