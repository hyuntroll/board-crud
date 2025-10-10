package pong.ios.boardcrud.domain.like.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.domain.like.dto.LikeResponse;
import pong.ios.boardcrud.domain.post.service.PostService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponse> likePost(@PathVariable Long postId, Authentication auth) {

        String username = auth.getName();

        try {

            postService.likePost(postId, username);

            return ResponseEntity.ok().build();

        }

        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<LikeResponse> unlikePost(@PathVariable Long postId, Authentication auth) {

        String username = auth.getName();

        try {
            return ResponseEntity.ok().body(postService.unlikePost(postId, username));
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
