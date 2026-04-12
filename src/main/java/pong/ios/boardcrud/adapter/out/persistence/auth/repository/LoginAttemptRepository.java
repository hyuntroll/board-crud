package pong.ios.boardcrud.adapter.out.persistence.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pong.ios.boardcrud.adapter.out.persistence.auth.entity.LoginAttemptEntity;

public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {
}
