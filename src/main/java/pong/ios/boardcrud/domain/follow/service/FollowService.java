package pong.ios.boardcrud.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.follow.domain.Follow;
import pong.ios.boardcrud.domain.follow.domain.FollowId;
import pong.ios.boardcrud.domain.follow.repository.FollowRepository;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void follow(String follower, String followee) {
        isSelfFollow(follower, followee);

        UserEntity followerEntity = getUser(follower);
        UserEntity followeeEntity = getUser(followee);

        FollowId id = new FollowId(followerEntity.getId(), followeeEntity.getId());
        if (!followRepository.existsById(id)) {
            followRepository.save(new Follow(id, followerEntity, followeeEntity));
        }
    }

    public void unfollow(String follower, String followee) {
        isSelfFollow(follower, followee);

        UserEntity followerEntity = getUser(follower);
        UserEntity followeeEntity = getUser(followee);

        FollowId id = new FollowId(followerEntity.getId(), followeeEntity.getId());
        if (followRepository.existsById(id)) {
            followRepository.deleteById(id);
        }
    }

    public void isSelfFollow(String follower, String followee) {
        if (follower == null || followee == null) {
            throw new NoSuchElementException("Follower or followee not found");
        }
        if (follower.equals(followee)) {
            throw new IllegalArgumentException("Follower and Followee cannot be the same");
        }
    }

    public UserEntity getUser(String follower) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(follower);
        return userEntity.orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
