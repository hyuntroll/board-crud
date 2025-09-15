package pong.ios.boardcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.like.Like;
import pong.ios.boardcrud.domain.entity.like.LikeId;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
