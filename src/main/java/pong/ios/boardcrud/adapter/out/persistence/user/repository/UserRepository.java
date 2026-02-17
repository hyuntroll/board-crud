package pong.ios.boardcrud.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
