package pong.ios.boardcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}