package pong.ios.boardcrud.application.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.user.CreateUserUseCase;
import pong.ios.boardcrud.application.port.in.user.GetUserUseCase;
import pong.ios.boardcrud.application.port.in.user.dto.CreateUserCommand;
import pong.ios.boardcrud.application.port.in.user.dto.UserResult;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.application.port.out.user.SaveUserPort;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements CreateUserUseCase, GetUserUseCase {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResult createUser(CreateUserCommand command) {
        if (loadUserPort.existsByEmail(command.email())) {
            throw new ApplicationException(UserStatusCode.DUPLICATE_EMAIL);
        }
        if (loadUserPort.existsByUsername(command.username())) {
            throw new ApplicationException(UserStatusCode.DUPLICATE_USERNAME);
        }

        User user = saveUserPort.save(
                User.builder()
                        .username(command.username())
                        .nickname(command.nickname())
                        .email(command.email())
                        .role(UserRoleType.USER)
                        .profile("/default/icon.png")
                        .birthDate(command.birthDate())
                        .password(passwordEncoder.encode(command.password()))
                        .build()
        );
        return UserResult.from(user);
    }

    @Override
    public UserResult getUser(Long userId) {
        User user = loadUserPort.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));

        return UserResult.from(user);
    }
}
