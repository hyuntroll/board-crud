package pong.ios.boardcrud.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.dto.comment.CommentRequest;
import pong.ios.boardcrud.dto.comment.CommentResponse;
import pong.ios.boardcrud.service.CommentService;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId, @RequestBody CommentRequest comment, Authentication auth) {
        String username = auth.getName();

        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(
                commentService.addComment(comment, username, postId)
            );

        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PatchMapping
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest comment, Authentication auth) {
        String username = auth.getName();

        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    commentService.updateComment(comment, username)
            );
        }
        catch ( NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

    }


    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestBody CommentRequest comment, Authentication auth) {
        String username = auth.getName();

        try {
            commentService.deleteComment(comment, username);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch ( NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

    }
}
