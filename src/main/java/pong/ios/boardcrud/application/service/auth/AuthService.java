package pong.ios.boardcrud.application.service.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.auth.LoginUseCase;
import pong.ios.boardcrud.application.port.in.auth.LogoutUseCase;
import pong.ios.boardcrud.application.port.in.auth.ReissueTokenUseCase;
import pong.ios.boardcrud.application.port.out.auth.DeleteRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.LoadRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.auth.LoginAttemptPort;
import pong.ios.boardcrud.application.port.out.auth.SaveRefreshTokenPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.auth.AuthStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.infra.security.jwt.JwtProvider;
import pong.ios.boardcrud.global.infra.security.jwt.exception.JwtStatusCode;
import pong.ios.boardcrud.global.infra.security.jwt.model.JwtPayload;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements LoginUseCase, LogoutUseCase, ReissueTokenUseCase {
    private final LoadRefreshTokenPort loadRefreshTokenPort;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final LoadUserPort loadUserPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final LoginAttemptPort loginAttemptPort;

    @Override
    public JwtPayload login(String loginId, String password) {
        User user = ((loginId.contains("@")) ?
            loadUserPort.findByEmail(loginId) :
            loadUserPort.findByUsername(loginId))
            .orElseThrow(() -> new ApplicationException(AuthStatusCode.INVALID_CREDENTIALS));

        Long userId = user.getId();
        if (loginAttemptPort.isBlocked(userId)) {
            int remainingMinutes = loginAttemptPort.getRemainingLockTime(userId);
            throw new ApplicationException(
                    AuthStatusCode.ACCOUNT_LOCK,
                    String.format("계정이 일시적으로 잠겼습니다. %s분 후 다시 시도하세요.", remainingMinutes)
            );
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            loginAttemptPort.recordFailure(userId);
            throw new ApplicationException(AuthStatusCode.INVALID_CREDENTIALS);
        }
        loginAttemptPort.resetAttempt(userId);

        // JWT 토큰 발급
        String access = jwtProvider.generateAccessToken(userId, user.getRole());
        String refresh = jwtProvider.generateRefreshToken(userId);
        saveRefreshTokenPort.save(userId, refresh);

        return new JwtPayload(access, refresh);
    }

    @Override
    public void logout(String refreshToken) {
        Long userId = jwtProvider.getUserId(refreshToken);

        if (!loadRefreshTokenPort.exists(userId, refreshToken)) {
            throw new ApplicationException(JwtStatusCode.EXPIRED_TOKEN);
        }

        deleteRefreshTokenPort.delete(userId, refreshToken);
    }

    @Override
    public JwtPayload reissueToken(String oldToken) {
        Long userId = jwtProvider.getUserId(oldToken);

        if (!loadRefreshTokenPort.exists(userId, oldToken)) {
            throw new ApplicationException(JwtStatusCode.EXPIRED_TOKEN);
        }

        if (loginAttemptPort.isBlocked(userId)) {
            throw new ApplicationException(AuthStatusCode.ACCOUNT_LOCK);
        }

        User user = loadUserPort.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));

        String access = jwtProvider.generateAccessToken(userId, user.getRole());
        String refresh = jwtProvider.generateRefreshToken(userId);
        saveRefreshTokenPort.save(userId, refresh);
        deleteRefreshTokenPort.delete(userId, oldToken);

        return new JwtPayload(access, refresh);
    }
}
