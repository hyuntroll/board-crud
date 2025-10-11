package pong.ios.boardcrud.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.domain.follow.service.FollowService;
import pong.ios.boardcrud.domain.user.dto.UserResponse;
import pong.ios.boardcrud.domain.user.serivce.UserService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        try {
            UserResponse user = userService.findByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<Void> follow(@PathVariable(name = "username") String followingName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            followService.follow(username, followingName);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("{username}/follow")
    public ResponseEntity<Void> unfollow(@PathVariable(name = "username") String followingName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            followService.unfollow(username, followingName);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
