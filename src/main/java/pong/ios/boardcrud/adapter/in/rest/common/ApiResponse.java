package pong.ios.boardcrud.adapter.in.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResponse {

    private ApiResponse() {
    }

    public static ResponseEntity<BaseResponse<Void>> ok(String message) {
        return ResponseEntity.ok(new BaseResponse<>(200, message, null));
    }

    public static ResponseEntity<BaseResponse<Void>> ok() {
        return ResponseEntity.ok(new BaseResponse<>(200, "success", null));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new BaseResponse<>(200, message, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        return ResponseEntity.ok(new BaseResponse<>(200, "success", data));
    }

    public static ResponseEntity<BaseResponse<Void>> created(String message) {
        return ResponseEntity
                .status(201)
                .body(new BaseResponse<>(201, message, null));
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(String message, T data) {
        return ResponseEntity
                .status(201)
                .body(new BaseResponse<>(201, message, data));
    }

    public static ResponseEntity<BaseResponse<Void>> badRequest(String message) {
        return ResponseEntity
                .status(400)
                .body(new BaseResponse<>(400, message, null));
    }

    public static ResponseEntity<BaseResponse<Void>> unauthorized(String message) {
        return ResponseEntity
                .status(401)
                .body(new BaseResponse<>(401, message, null));
    }

    public static ResponseEntity<BaseResponse<Void>> forbidden(String message) {
        return ResponseEntity
                .status(403)
                .body(new BaseResponse<>(403, message, null));
    }

    public static ResponseEntity<BaseResponse<Void>> notFound(String message) {
        return ResponseEntity
                .status(404)
                .body(new BaseResponse<>(404, message, null));
    }

    public static <T> ResponseEntity<BaseResponse<T>> of(int status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(new BaseResponse<>(status, message, data));
    }

    public static ResponseEntity<BaseResponse<Void>> of(int status, String message) {
        return ResponseEntity
                .status(status)
                .body(new BaseResponse<>(status, message, null));
    }
}
