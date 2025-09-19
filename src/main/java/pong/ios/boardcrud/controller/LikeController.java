package pong.ios.boardcrud.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.service.PostService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            postService.likePost(postId, username);
            return ResponseEntity.ok().build();

        }

        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            postService.unlikePost(postId, username);
            return ResponseEntity.ok().build();

        }

        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
