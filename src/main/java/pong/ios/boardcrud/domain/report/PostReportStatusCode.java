package pong.ios.boardcrud.domain.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pong.ios.boardcrud.global.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum PostReportStatusCode implements StatusCode {
    REPORT_ALREADY_EXISTS("이미 신고한 게시글입니다.", HttpStatus.CONFLICT),
    REPORT_NOT_FOUND("신고 내역이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    REPORT_FORBIDDEN("신고 내역에 접근할 수 없습니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
