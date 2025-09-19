package pong.ios.boardcrud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain.entity.follow.Follow;
import pong.ios.boardcrud.domain.entity.follow.FollowId;
import pong.ios.boardcrud.domain.entity.user.UserEntity;


@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    int countByFollower(UserEntity follower);

    int countByFollowing(UserEntity following);


}
