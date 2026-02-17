package pong.ios.boardcrud.adapter.in.rest.user.dto.request;

import jakarta.validation.constraints.*;
import pong.ios.boardcrud.application.port.in.user.dto.CreateUserCommand;

import java.time.LocalDate;

public record SignupUserRequest(
    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "영어만 입력 가능합니다.")
    String username,

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "영문과 숫자만 입력 가능합니다.")
    String nickname,

    @NotNull
    @Past
    LocalDate birthDate,

    @NotBlank
    @Size(min = 8)
    String password
) {
    public CreateUserCommand toCommand() {
        return new CreateUserCommand(email, username, nickname, birthDate, password);
    }
}
