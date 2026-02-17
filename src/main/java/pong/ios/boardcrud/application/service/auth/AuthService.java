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

    @Override
    public JwtPayload login(String loginId, String password) {
        User user = ((loginId.contains("@")) ?
            loadUserPort.findByEmail(loginId) :
            loadUserPort.findByUsername(loginId))
            .orElseThrow(() -> new ApplicationException(AuthStatusCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException(AuthStatusCode.INVALID_CREDENTIALS);
        }

        String access = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        String refresh = jwtProvider.generateRefreshToken(user.getId());
        saveRefreshTokenPort.save(refresh);

        return new JwtPayload(access, refresh);
    }

    @Override
    public void logout(String refreshToken) {
        if (!loadRefreshTokenPort.exists(refreshToken)) {
            throw new ApplicationException(JwtStatusCode.EXPIRED_TOKEN);
        }

        deleteRefreshTokenPort.delete(refreshToken);
    }

    @Override
    public JwtPayload reissueToken(String oldToken) {
        if (!loadRefreshTokenPort.exists(oldToken)) {
            throw new ApplicationException(JwtStatusCode.EXPIRED_TOKEN);
        }

        Long userId = jwtProvider.getUserId(oldToken);
        User user = loadUserPort.findById(userId)
            .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));

        String access = jwtProvider.generateAccessToken(userId, user.getRole());
        String refresh = jwtProvider.generateRefreshToken(userId);
        saveRefreshTokenPort.save(refresh);

        return new JwtPayload(access, refresh);
    }
}
