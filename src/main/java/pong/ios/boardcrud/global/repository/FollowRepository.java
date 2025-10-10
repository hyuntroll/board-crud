package pong.ios.boardcrud.global.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pong.ios.boardcrud.domain_1.entity.follow.Follow;
import pong.ios.boardcrud.domain_1.entity.follow.FollowId;
import pong.ios.boardcrud.domain.user.domain.UserEntity;


@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    int countByFollower(UserEntity follower);

    int countByFollowing(UserEntity following);


}
