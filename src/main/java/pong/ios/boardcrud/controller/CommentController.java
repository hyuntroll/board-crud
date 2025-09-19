package pong.ios.boardcrud.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.dto.CommentRequest;
import pong.ios.boardcrud.dto.CommentResponse;
import pong.ios.boardcrud.service.CommentService;
import pong.ios.boardcrud.service.PostService;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId, CommentRequest comment) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

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
    public ResponseEntity<CommentResponse> updateComment(CommentRequest comment) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

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
    public ResponseEntity<String> deleteComment(CommentRequest comment) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

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
