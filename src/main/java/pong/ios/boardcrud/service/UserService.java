package pong.ios.boardcrud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.JoinRequest;
import pong.ios.boardcrud.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

}
