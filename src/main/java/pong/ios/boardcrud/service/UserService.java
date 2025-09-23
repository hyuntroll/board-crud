package pong.ios.boardcrud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.follow.Follow;
import pong.ios.boardcrud.domain.entity.follow.FollowId;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.join.JoinRequest;
import pong.ios.boardcrud.dto.user.UserResponse;
import pong.ios.boardcrud.repository.FollowRepository;
import pong.ios.boardcrud.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean singUp(JoinRequest joinRequest) {
        String username = joinRequest.getUsername();
        String password = joinRequest.getPassword();
        String email = joinRequest.getEmail();

        if (userRepository.existsByUsername(username) ||
            userRepository.existsByEmail(username)) { return false; }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password((bCryptPasswordEncoder.encode(password)))
                .email(email)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        return true;
    }

    public UserResponse findByUsername(String username) throws NoSuchElementException {

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) { throw new NoSuchElementException("User not found"); }

        return new UserResponse(user);

    }

    public void follow(String follower, String followee) throws NoSuchElementException, IllegalArgumentException {

        if (follower.equals(followee)) {
            throw new IllegalArgumentException("Follower and Followee cannot be the same");
        }

        UserEntity followerEntity = userRepository.findByUsername(follower);
        UserEntity followeeEntity = userRepository.findByUsername(followee);

        if (followerEntity == null || followeeEntity == null) {
            throw new NoSuchElementException("Follower or followee not found");
        }

        FollowId id =  new FollowId(followerEntity.getId(), followeeEntity.getId());
        if (!followRepository.existsById(id)) {
            followRepository.save(new Follow(id, followerEntity, followeeEntity));
        }

    }

    public void unfollow(String follower, String followee) throws NoSuchElementException, IllegalArgumentException {

        if (follower.equals(followee)) {
            throw new IllegalArgumentException("Follower and Followee cannot be the same");
        }

        UserEntity followerEntity = userRepository.findByUsername(follower);
        UserEntity followeeEntity = userRepository.findByUsername(followee);

        if (followerEntity == null || followeeEntity == null) {
            throw new NoSuchElementException("Follower or followee not found");
        }

        FollowId id =  new FollowId(followerEntity.getId(), followeeEntity.getId());
        if (followRepository.existsById(id)) {
            followRepository.deleteById(id);
        }

    }


}
