package pong.ios.boardcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.user.BoardUser;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
}