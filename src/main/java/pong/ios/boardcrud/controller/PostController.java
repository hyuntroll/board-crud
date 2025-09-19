package pong.ios.boardcrud.controller;

import com.sun.net.httpserver.HttpContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.dto.CommentRequest;
import pong.ios.boardcrud.dto.PostRequest;
import pong.ios.boardcrud.dto.PostResponse;
import pong.ios.boardcrud.service.CommentService;
import pong.ios.boardcrud.service.PostService;

import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<PostResponse> viewPost(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(postService.getPostById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found");
        }
    }


    @PostMapping
    public ResponseEntity<Void> writePost(@RequestBody PostRequest post) { // 이거 어노테이션 있어도 좋을듯
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (postService.addPost(post, username)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            postService.deletePost(postId, username);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequest post) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            postService.modifyPost(postId, post, username);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

    }

}
