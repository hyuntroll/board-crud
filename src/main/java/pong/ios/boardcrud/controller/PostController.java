package pong.ios.boardcrud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pong.ios.boardcrud.dto.post.PostRequest;
import pong.ios.boardcrud.dto.post.PostResponse;
import pong.ios.boardcrud.service.PostService;

import java.nio.file.AccessDeniedException;
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
    public ResponseEntity<PostResponse> writePost(@RequestBody PostRequest post, Authentication auth) { // 이거 어노테이션 있어도 좋을듯

        String username = auth.getName();

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(post, username));

    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication auth) {

        String username = auth.getName();

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
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostRequest post, Authentication auth) {

        String username = auth.getName();

        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(postService.modifyPost(postId, post, username));

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }

    }

}
