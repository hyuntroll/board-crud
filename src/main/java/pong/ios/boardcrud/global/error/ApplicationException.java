package pong.ios.boardcrud.global.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final StatusCode statusCode;
    private String code;

    public ApplicationException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.code = ((Enum<?>) statusCode).name();
    }

    public ApplicationException(StatusCode statusCode, Throwable cause) {
        super(statusCode.getMessage(), cause);
        this.statusCode = statusCode;
        this.code = ((Enum<?>) statusCode).name();
    }

    public ApplicationException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.code = ((Enum<?>) statusCode).name();
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
}
