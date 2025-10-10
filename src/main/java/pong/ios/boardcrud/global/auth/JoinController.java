package pong.ios.boardcrud.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.global.auth.dto.JoinRequest;
import pong.ios.boardcrud.domain.user.serivce.UserService;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/user")
public class JoinController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> singUp(JoinRequest joinRequest) {
        if(userService.singUp(joinRequest)) {
            return ResponseEntity.ok("Verification email send to " + joinRequest.getEmail());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        if (userService.verifyEmail(email, code.toUpperCase())) {
            return ResponseEntity.ok("회원가입 성공");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
