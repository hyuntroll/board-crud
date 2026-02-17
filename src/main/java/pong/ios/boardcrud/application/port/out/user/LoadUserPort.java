package pong.ios.boardcrud.application.port.out.user;

import pong.ios.boardcrud.domain.user.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
