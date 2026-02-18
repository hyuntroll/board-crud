package pong.ios.boardcrud.application.port.in.user;

import pong.ios.boardcrud.application.port.in.user.dto.CreateUserCommand;
import pong.ios.boardcrud.application.port.in.user.dto.UserResult;

public interface CreateUserUseCase {
    UserResult createUser(CreateUserCommand command);
}
