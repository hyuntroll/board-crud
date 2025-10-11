package pong.ios.boardcrud.domain.post.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.post.dto.PostRequest;
import pong.ios.boardcrud.domain.post.dto.PostResponse;
import pong.ios.boardcrud.domain.post.repository.LikeRepository;
import pong.ios.boardcrud.domain.post.repository.PostRepository;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostResponse getPostById(Long id) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);

        if (post != null) {
            return new PostResponse(post);
        }

        throw new NoSuchElementException();
    }

    public List<PostResponse> getAllPosts() {

        ArrayList<PostResponse> postResponses = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            postResponses.add(new PostResponse(post));
        }

        return postResponses;

    }

    public PostResponse addPost(PostRequest postRequest, String writer) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        Optional<UserEntity> writerEntity = userRepository.findByUsername(writer);
        if (writerEntity.isEmpty()) throw new NoSuchElementException();

        Post post = new Post(title, content, writerEntity.get());

        postRepository.save(post);
        return new PostResponse(post);
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

    public PostResponse modifyPost(Long id, PostRequest postRequest, String writer) throws AccessDeniedException, NoSuchElementException {

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

        return new PostResponse(post);
    }



}
