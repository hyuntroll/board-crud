package pong.ios.boardcrud.adapter.in.rest.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pong.ios.boardcrud.adapter.in.rest.auth.docs.AuthControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LoginRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LogoutRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.ReissueRequest;
import pong.ios.boardcrud.application.port.in.auth.LoginUseCase;
import pong.ios.boardcrud.application.port.in.auth.LogoutUseCase;
import pong.ios.boardcrud.application.port.in.auth.ReissueTokenUseCase;
import pong.ios.boardcrud.adapter.in.rest.common.BaseResponse;
import pong.ios.boardcrud.global.infra.security.jwt.model.JwtPayload;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerDocs {
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;

    @PostMapping("/login")
    @Override
    public ResponseEntity<BaseResponse<JwtPayload>> login(@Valid @RequestBody LoginRequest request) {
        return BaseResponse.ok(
            loginUseCase.login(request.loginId(), request.password())
        );
    }

    @PostMapping("/logout")
    @Override
    public ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
        logoutUseCase.logout(request.refreshToken());

        return BaseResponse.ok("정상적으로 로그아웃 되었습니다.");
    }

    @PostMapping("/reissue")
    @Override
    public ResponseEntity<BaseResponse<JwtPayload>> reissue(@Valid @RequestBody ReissueRequest request) {
        return BaseResponse.ok(
                reissueTokenUseCase.reissueToken(request.refreshToken())
        );
    }
}
