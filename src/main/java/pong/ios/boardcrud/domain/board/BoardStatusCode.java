package pong.ios.boardcrud.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum BoardStatusCode implements StatusCode {
    BOARD_NOT_FOUND("게시판이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ALREADY_EXISTS_GRANT("이미 권한을 가지고 있습니다.", HttpStatus.CONFLICT),
    BOARD_MANAGER_NOT_FOUND("게시판 관리자 권한이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    BOARD_MANAGER_FORBIDDEN("게시판 관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
