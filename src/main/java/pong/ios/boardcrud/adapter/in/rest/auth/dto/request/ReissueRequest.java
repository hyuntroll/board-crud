package pong.ios.boardcrud.adapter.in.rest.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "토큰 재발급 요청")
public record ReissueRequest(
        @Schema(description = "기존 리프레시 토큰", example = "eyJhbGciOiJIUzI1Ni...")
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        String refreshToken
) {}
