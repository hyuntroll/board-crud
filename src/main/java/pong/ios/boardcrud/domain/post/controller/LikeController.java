package pong.ios.boardcrud.domain.post.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.domain.post.dto.LikeResponse;
import pong.ios.boardcrud.domain.post.service.LikeService;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponse> likePost(@PathVariable Long postId, Authentication auth) {

        String username = auth.getName();

        try {
            return ResponseEntity.status(HttpStatus.OK).body(likeService.likePost(postId, username));
        }

        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<LikeResponse> unlikePost(@PathVariable Long postId, Authentication auth) {

        String username = auth.getName();

        try {
            return ResponseEntity.ok().body(likeService.unlikePost(postId, username));
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
