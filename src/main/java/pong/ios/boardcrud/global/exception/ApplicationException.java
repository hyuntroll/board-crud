package pong.ios.boardcrud.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final StatusCode statusCode;
    private final String code;

    public ApplicationException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.code = extractCode(statusCode);
    }

    public ApplicationException(StatusCode statusCode, Throwable cause) {
        super(statusCode.getMessage(), cause);
        this.statusCode = statusCode;
        this.code = extractCode(statusCode);
    }

    public ApplicationException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.code = extractCode(statusCode);
    }

    public static ApplicationException of(StatusCode statusCode) {
        return new ApplicationException(statusCode);
    }

    public static ApplicationException of(StatusCode statusCode, Throwable cause) {
        return new ApplicationException(statusCode, cause);
    }

    public static ApplicationException of(StatusCode statusCode, String customMessage) {
        return new ApplicationException(statusCode, customMessage);
    }

    private String extractCode(StatusCode statusCode) {
        if (statusCode instanceof Enum<?>) return ((Enum<?>) statusCode).name();

        return null;
    }
}
