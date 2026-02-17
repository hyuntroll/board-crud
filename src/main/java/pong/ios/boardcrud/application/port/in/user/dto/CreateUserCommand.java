package pong.ios.boardcrud.application.port.in.user.dto;

import java.time.LocalDate;

public record CreateUserCommand(
    String email,
    String username,
    String nickname,
    LocalDate birthDate,
    String password
) {}
