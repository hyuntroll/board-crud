package pong.ios.boardcrud.application.port.in.user;

import pong.ios.boardcrud.application.port.in.user.dto.UserResult;

public interface GetUserUseCase {
    UserResult getUser(Long userId);
}
