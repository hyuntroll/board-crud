package pong.ios.boardcrud.application.port.in.user.dto;

import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDate;


public record UserResult(
    Long id,
    String email,
    String username,
    String nickname,
    String profile,
    LocalDate birthDate
) {    
    public static UserResult from(User user) {
        return new UserResult(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getNickname(),
                user.getProfile(),
                user.getBirthDate()
        );
    }
}
