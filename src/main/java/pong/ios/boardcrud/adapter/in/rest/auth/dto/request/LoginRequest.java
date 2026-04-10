package pong.ios.boardcrud.adapter.in.rest.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @Schema(description = "로그인 아이디(이메일 또는 username)", example = "user@example.com")
        @NotBlank(message = "아이디 또는 이메일을 입력하세요.")
        String loginId,

        @Schema(description = "비밀번호", example = "password123!")
        @NotBlank(message = "비밀번호를 입력하세요.")
        String password
) {}
