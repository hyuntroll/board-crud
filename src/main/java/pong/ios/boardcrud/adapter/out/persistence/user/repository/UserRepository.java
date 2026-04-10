package pong.ios.boardcrud.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
