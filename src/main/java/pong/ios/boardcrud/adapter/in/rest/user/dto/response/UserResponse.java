package pong.ios.boardcrud.adapter.in.rest.user.dto.response;


import pong.ios.boardcrud.application.port.in.user.dto.UserResult;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String email,
        String username,
        String nickname,
        String profile,
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
