package pong.ios.boardcrud.domain.post.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.like.domain.Like;
import pong.ios.boardcrud.domain.like.domain.LikeId;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.domain.like.dto.LikeResponse;
import pong.ios.boardcrud.domain.post.dto.PostRequest;
import pong.ios.boardcrud.domain.post.dto.PostResponse;
import pong.ios.boardcrud.domain.like.repository.LikeRepository;
import pong.ios.boardcrud.domain.post.repository.PostRepository;
import pong.ios.boardcrud.domain.user.repository.UserRepository;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final LikeRepository likeRepository;

    public PostResponse getPostById(Long id) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);

        if (post != null) {
            return new PostResponse(post, countPostLike(id));
        }

        throw new NoSuchElementException();
    }

    public List<PostResponse> getAllPosts() {

        ArrayList<PostResponse> postResponses = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            postResponses.add(new PostResponse(post, likeRepository.countByPost(post)));
        }

        return postResponses;

    }

    public PostResponse addPost(PostRequest postRequest, String writer) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        UserEntity writerEntity = userRepository.findByUsername(writer);
        Post post = new Post(title, content, writerEntity);

        postRepository.save(post);
        return new PostResponse(post, 0);
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

        return new PostResponse(post, countPostLike(id));
    }

    public LikeResponse likePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        UserEntity user = userRepository.findByUsername(username);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (!likeRepository.existsByLikerAndPost(user, post)) {
            LikeId likeId = new LikeId(post.getId(), user.getId());
            likeRepository.save(new Like(likeId, user, post));
        }

        return new LikeResponse(id, countPostLike(id));

    }

    public LikeResponse unlikePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        UserEntity user = userRepository.findByUsername(username);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (likeRepository.existsByLikerAndPost(user, post)) {
            likeRepository.deleteByLikerAndPost(user, post);
        }

        return new LikeResponse(id, countPostLike(id));

    }

    public int countPostLike(Long postId) throws NoSuchElementException {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        return likeRepository.countByPost(post);
    }

}
