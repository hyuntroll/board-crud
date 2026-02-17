package pong.ios.boardcrud.adapter.in.rest.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.adapter.in.rest.user.docs.UserControllerDocs;
import pong.ios.boardcrud.adapter.in.rest.user.dto.request.SignupUserRequest;
import pong.ios.boardcrud.adapter.in.rest.user.dto.response.UserResponse;
import pong.ios.boardcrud.application.port.in.user.CreateUserUseCase;
import pong.ios.boardcrud.application.port.in.user.GetUserUseCase;
import pong.ios.boardcrud.application.port.in.user.dto.UserResult;
import pong.ios.boardcrud.global.data.BaseResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<UserResponse>> signupUser(
            @Valid @RequestBody SignupUserRequest request
    ) {
        UserResult result = createUserUseCase.createUser(request.toCommand());

        return BaseResponse.created(
                "계정이 정상적으로 생성되었습니다.",
                UserResponse.from(result)
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<BaseResponse<UserResponse>> getUser() {
        return null;
    }

}
