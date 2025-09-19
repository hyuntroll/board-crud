package pong.ios.boardcrud.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.comment.Comment;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.CommentRequest;
import pong.ios.boardcrud.dto.CommentResponse;
import pong.ios.boardcrud.repository.CommentRepository;
import pong.ios.boardcrud.repository.PostRepository;
import pong.ios.boardcrud.repository.UserRepository;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public CommentResponse addComment(CommentRequest commentRequest, String username, Long postId ) throws NoSuchElementException {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new NoSuchElementException("post not found");
        }
        UserEntity user = userRepository.findByUsername(username);
        Post post = postOptional.get();


        Comment comment = new Comment(commentRequest.getContent(), user, post);
        post.addComment(comment);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment);

    }

    public CommentResponse updateComment(CommentRequest commentRequest, String username) throws NoSuchElementException, AccessDeniedException {

        Optional<Comment> commentOptional = commentRepository.findById(commentRequest.getId());
        if (commentOptional.isEmpty()) {
            throw new NoSuchElementException("Comment not found");
        }

        Comment comment = commentOptional.get();

        if (!comment.getCommenter().getUsername().equals(username)) {
            throw new AccessDeniedException("Access denied");
        }

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);

        return new CommentResponse(comment);

    }

    public void deleteComment(CommentRequest commentRequest, String username) throws NoSuchElementException, AccessDeniedException {

        Optional<Comment> commentOptional = commentRepository.findById(commentRequest.getId());
        if (commentOptional.isEmpty()) {
            throw new NoSuchElementException("Comment not found");
        }

        Comment comment = commentOptional.get();

        if (!comment.getCommenter().getUsername().equals(username)) {
            throw new AccessDeniedException("Access denied");
        }

        commentRepository.delete(comment);

    }

}
