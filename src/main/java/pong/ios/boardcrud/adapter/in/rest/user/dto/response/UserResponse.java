package pong.ios.boardcrud.adapter.in.rest.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.user.dto.UserResult;

import java.time.LocalDate;

@Schema(description = "유저 응답")
public record UserResponse(
        @Schema(description = "유저 ID", example = "1")
        Long id,
        @Schema(description = "이메일", example = "user@example.com")
        String email,
        @Schema(description = "아이디", example = "hyuntroll")
        String username,
        @Schema(description = "닉네임", example = "hyun")
        String nickname,
        @Schema(description = "프로필 이미지 경로", example = "/default/icon.png")
        String profile,
        @Schema(description = "생년월일", example = "2000-01-01")
        LocalDate birthDate
) {
    public static UserResponse from(UserResult result) {
        return new UserResponse(
                result.id(),
                result.email(),
                result.username(),
                result.nickname(),
                result.profile(),
                result.birthDate()
        );
    }
}
