package pong.ios.boardcrud.application.port.out.user;

import pong.ios.boardcrud.domain.user.User;

public interface SaveUserPort {
    User save(User user);
}
