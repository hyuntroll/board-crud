package pong.ios.boardcrud.domain.user.serivce;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.user.dto.UserResponse;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findByUsername(String username) throws NoSuchElementException {

        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isEmpty()) { throw new NoSuchElementException("User not found"); }

        return new UserResponse(user.get());

    }




}
