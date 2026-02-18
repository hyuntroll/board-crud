package pong.ios.boardcrud.global.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        StatusCode statusCode = ex.getStatusCode();

        ErrorResponse error = ErrorResponse.of(
                statusCode.getHttpStatus().value(),
                ex.getCode(),
                ex.getMessage() != null ? ex.getMessage() : statusCode.getMessage()
        );

        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                "요청 값이 유효하지 않습니다"
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage() != null ? error.getDefaultMessage() : "잘못된 입력값입니다.";
            details.put(field, message);
        });

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                "요청값이 유효하지 않습니다.",
                details
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(HttpClientErrorException.MethodNotAllowed.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(HttpClientErrorException.MethodNotAllowed ex) {

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.ENDPOINT_NOT_FOUND.getHttpStatus().value(),
                CommonStatusCode.ENDPOINT_NOT_FOUND.name(),
                CommonStatusCode.ENDPOINT_NOT_FOUND.getMessage()
        );

        return ResponseEntity
                .status(CommonStatusCode.ENDPOINT_NOT_FOUND.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            details.put(fieldName, message);
        });

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                "요청값이 유효하지 않습니다.",
                details
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                "필수 파라미터가 누락되었습니다: " + ex.getParameterName()
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                "파라미터 타입이 잘못되었습니다: " + ex.getName()
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INVALID_ARGUMENT.getHttpStatus().value(),
                CommonStatusCode.INVALID_ARGUMENT.name(),
                ex.getMessage() != null ? ex.getMessage() : "잘못된 요청 파라미터입니다."
        );

        return ResponseEntity
                .status(CommonStatusCode.INVALID_ARGUMENT.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.ENDPOINT_NOT_FOUND.getHttpStatus().value(),
                CommonStatusCode.ENDPOINT_NOT_FOUND.name(),
                CommonStatusCode.ENDPOINT_NOT_FOUND.getMessage()
        );

        return ResponseEntity
                .status(CommonStatusCode.ENDPOINT_NOT_FOUND.getHttpStatus())
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error occurred: ", ex);

        ErrorResponse error = ErrorResponse.of(
                CommonStatusCode.INTERNAL_SERVER_ERROR.getHttpStatus().value(),
                CommonStatusCode.INTERNAL_SERVER_ERROR.name(),
                CommonStatusCode.INTERNAL_SERVER_ERROR.getMessage()
        );

        return ResponseEntity
                .status(CommonStatusCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(error);
    }

}