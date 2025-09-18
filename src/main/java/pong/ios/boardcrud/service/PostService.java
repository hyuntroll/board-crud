package pong.ios.boardcrud.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.PostRequest;
import pong.ios.boardcrud.dto.PostResponse;
import pong.ios.boardcrud.repository.PostRepository;
import pong.ios.boardcrud.repository.UserRepository;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public PostResponse viewPost(Long id) throws NoSuchElementException {
        Post post = postRepository.findPostById(id);
        if (post != null) {
            return new PostResponse(post);
        }
        throw new NoSuchElementException();
    }

    public boolean addPost(PostRequest postRequest, String writer) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        UserEntity writerEntity = userRepository.findByUsername(writer);
        Post post = new Post(title, content, writerEntity);

        postRepository.save(post);
        return true;
    }

    public void deletePost(Long id, String writer) throws AccessDeniedException, NoSuchElementException {
        Post post = postRepository.findPostById(id);
        if  (post == null) {
            throw new NoSuchElementException("Post not found");
        }
        if (!post.getWriter().getUsername().equals(writer)) {
            throw new AccessDeniedException("User has no permission to delete this post");
        }

        postRepository.delete(post);
    }

    public void modifyPost(Long id, PostRequest postRequest, String writer) throws AccessDeniedException, NoSuchElementException {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (!post.getWriter().getUsername().equals(writer)) {
            throw new  AccessDeniedException("User has no permission to modify this post");
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        postRepository.save(post);

    }

}
