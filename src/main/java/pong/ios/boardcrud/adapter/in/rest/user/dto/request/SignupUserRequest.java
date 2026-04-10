package pong.ios.boardcrud.adapter.in.rest.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import pong.ios.boardcrud.application.port.in.user.dto.CreateUserCommand;

import java.time.LocalDate;

@Schema(description = "회원가입 요청")
public record SignupUserRequest(
    @Schema(description = "이메일", example = "user@example.com")
    @NotBlank
    @Email
    String email,

    @Schema(description = "아이디(영문)", example = "hyuntroll")
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "영어만 입력 가능합니다.")
    String username,

    @Schema(description = "닉네임(영문+숫자)", example = "hyuntroll01")
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "영문과 숫자만 입력 가능합니다.")
    String nickname,

    @Schema(description = "생년월일", example = "2000-01-01")
    @NotNull
    @Past
    LocalDate birthDate,

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank
    @Size(min = 8)
    String password
) {
    public CreateUserCommand toCommand() {
        return new CreateUserCommand(email, username, nickname, birthDate, password);
    }
}
