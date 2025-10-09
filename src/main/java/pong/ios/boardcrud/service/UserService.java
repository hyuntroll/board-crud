package pong.ios.boardcrud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisService redisService;

    private final MailService mailService;

    private int mailTimeout = 600000;

    public boolean singUp(JoinRequest joinRequest) {
        String username = joinRequest.getUsername();
        String email = joinRequest.getEmail();
        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        if (userRepository.existsByUsername(username) ||
            userRepository.existsByEmail(username)) { return false; }

        redisService.saveTempUser(joinRequest, mailTimeout);
        mailService.sendVerificationEmail(email);

        return true;
    }

    public boolean verifyEmail(String email, String code) {
        if (!redisService.verifyEmailCode(email, code)) return false;

        Optional<JoinRequest> optionalDto = redisService.findTempUser(email);
        if (optionalDto.isPresent()) {
            JoinRequest joinRequest = optionalDto.get();
            UserEntity user = UserEntity.builder()
                    .username(joinRequest.getUsername())
                    .password(joinRequest.getPassword())
                    .email(joinRequest.getEmail())
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);

            redisService.deleteVerifyEmail(email);
            redisService.deleteTempUser(joinRequest);
            return true;
        }

        return false;
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
