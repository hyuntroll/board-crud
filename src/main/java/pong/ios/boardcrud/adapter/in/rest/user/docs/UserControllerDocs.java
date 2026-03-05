package pong.ios.boardcrud.adapter.in.rest.user.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.user.dto.request.SignupUserRequest;
import pong.ios.boardcrud.adapter.in.rest.user.dto.response.UserResponse;
import pong.ios.boardcrud.global.data.BaseResponse;

@Tag(name = "User", description = "회원 API")
public interface UserControllerDocs {
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청값 검증 실패"),
            @ApiResponse(responseCode = "409", description = "중복 이메일/아이디")
    })
    ResponseEntity<BaseResponse<UserResponse>> signupUser(@RequestBody SignupUserRequest request);

    @Operation(summary = "내 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<BaseResponse<UserResponse>> getUser();
}
