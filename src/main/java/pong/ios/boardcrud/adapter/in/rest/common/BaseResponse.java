package pong.ios.boardcrud.adapter.in.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T>(
        int status,
        String message,
        T data
) {
    // data 없는 생성자 오버로드용 private 생성자
    private BaseResponse(int status, String message) {
        this(status, message, null);
    }

    // 200 - data 없음
    public static ResponseEntity<BaseResponse<Void>> ok(String message) {
        return ResponseEntity.ok(new BaseResponse<>(200, message));
    }

    // 200 - data 없음, 기본 메시지
    public static ResponseEntity<BaseResponse<Void>> ok() {
        return ResponseEntity.ok(new BaseResponse<>(200, "success"));
    }

    // 200 - data 있음
    public static <T> ResponseEntity<BaseResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new BaseResponse<>(200, message, data));
    }

    // 200 - data 있음, 기본 메시지
    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        return ResponseEntity.ok(new BaseResponse<>(200, "success", data));
    }

    // 201 - data 없음
    public static ResponseEntity<BaseResponse<Void>> created(String message) {
        return ResponseEntity
                .status(201)
                .body(new BaseResponse<>(201, message));
    }

    // 201 - data 있음
    public static <T> ResponseEntity<BaseResponse<T>> created(String message, T data) {
        return ResponseEntity
                .status(201)
                .body(new BaseResponse<>(201, message, data));
    }

    // 400 - data 없음
    public static ResponseEntity<BaseResponse<Void>> badRequest(String message) {
        return ResponseEntity
                .status(400)
                .body(new BaseResponse<>(400, message));
    }

    // 401 - data 없음
    public static ResponseEntity<BaseResponse<Void>> unauthorized(String message) {
        return ResponseEntity
                .status(401)
                .body(new BaseResponse<>(401, message));
    }

    // 403 - data 없음
    public static ResponseEntity<BaseResponse<Void>> forbidden(String message) {
        return ResponseEntity
                .status(403)
                .body(new BaseResponse<>(403, message));
    }

    // 404 - data 없음
    public static ResponseEntity<BaseResponse<Void>> notFound(String message) {
        return ResponseEntity
                .status(404)
                .body(new BaseResponse<>(404, message));
    }

    // 공통 - 모든 status, data 있음
    public static <T> ResponseEntity<BaseResponse<T>> of(int status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(new BaseResponse<>(status, message, data));
    }

    // 공통 - 모든 status, data 없음
    public static ResponseEntity<BaseResponse<Void>> of(int status, String message) {
        return ResponseEntity
                .status(status)
                .body(new BaseResponse<>(status, message));
    }
}