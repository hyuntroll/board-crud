package pong.ios.boardcrud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pong.ios.boardcrud.dto.join.JoinRequest;
import pong.ios.boardcrud.service.UserService;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping("/user")
public class JoinController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> singUp(JoinRequest joinRequest) {
        if(userService.singUp(joinRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
