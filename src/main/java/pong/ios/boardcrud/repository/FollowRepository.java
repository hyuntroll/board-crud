package pong.ios.boardcrud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.follow.Follow;
import pong.ios.boardcrud.domain.entity.follow.FollowId;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}
