package pong.ios.boardcrud.adapter.in.rest.auth.docs;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LoginRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LogoutRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.ReissueRequest;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.infra.security.jwt.model.JwtPayload;

public interface AuthControllerDocs {
    @PostMapping("/login")
    ResponseEntity<BaseResponse<JwtPayload>> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/logout")
    ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest request);

    @PostMapping("/reissue")
    ResponseEntity<BaseResponse<JwtPayload>> reissue(@Valid @RequestBody ReissueRequest request);
}
