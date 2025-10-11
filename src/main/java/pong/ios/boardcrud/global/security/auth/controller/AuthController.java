package pong.ios.boardcrud.global.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pong.ios.boardcrud.global.security.auth.dto.JoinRequest;
import pong.ios.boardcrud.global.security.auth.serivce.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> singUp(JoinRequest joinRequest) {
        if(authService.singUp(joinRequest)) {
            return ResponseEntity.ok("Verification email send to " + joinRequest.getEmail());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email, @RequestParam String code) {
        if (authService.verifyEmail(email, code.toUpperCase())) {
            return ResponseEntity.ok("회원가입 성공");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
