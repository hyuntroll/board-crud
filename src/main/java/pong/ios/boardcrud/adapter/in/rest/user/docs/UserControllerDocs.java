package pong.ios.boardcrud.adapter.in.rest.user.docs;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pong.ios.boardcrud.adapter.in.rest.user.dto.request.SignupUserRequest;
import pong.ios.boardcrud.adapter.in.rest.user.dto.response.UserResponse;
import pong.ios.boardcrud.global.data.BaseResponse;

public interface UserControllerDocs {
    ResponseEntity<BaseResponse<UserResponse>> signupUser(@RequestBody SignupUserRequest request);

    ResponseEntity<BaseResponse<UserResponse>> getUser();
}
