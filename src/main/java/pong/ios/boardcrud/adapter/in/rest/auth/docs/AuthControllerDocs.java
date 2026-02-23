package pong.ios.boardcrud.adapter.in.rest.auth.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LoginRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.LogoutRequest;
import pong.ios.boardcrud.adapter.in.rest.auth.dto.request.ReissueRequest;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.infra.security.jwt.model.JwtPayload;

@Tag(name = "Auth", description = "인증 API")
public interface AuthControllerDocs {
    @Operation(summary = "로그인", description = "아이디/이메일 + 비밀번호로 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패(아이디/비밀번호 불일치)"),
            @ApiResponse(responseCode = "423", description = "계정 잠금")
    })
    @PostMapping("/login")
    ResponseEntity<BaseResponse<JwtPayload>> login(@Valid @RequestBody LoginRequest request);

    @Operation(summary = "로그아웃", description = "리프레시 토큰을 무효화합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    ResponseEntity<BaseResponse<Void>> logout(@Valid @RequestBody LogoutRequest request);

    @Operation(summary = "토큰 재발급", description = "RTR 방식으로 Access/Refresh 토큰을 재발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "401", description = "만료/유효하지 않은 토큰"),
            @ApiResponse(responseCode = "423", description = "계정 잠금")
    })
    @PostMapping("/reissue")
    ResponseEntity<BaseResponse<JwtPayload>> reissue(@Valid @RequestBody ReissueRequest request);
}
