package pong.ios.boardcrud.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pong.ios.boardcrud.domain.entity.comment.Comment;
import pong.ios.boardcrud.domain.entity.like.Like;
import pong.ios.boardcrud.domain.entity.like.LikeId;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.PostRequest;
import pong.ios.boardcrud.dto.PostResponse;
import pong.ios.boardcrud.repository.LikeRepository;
import pong.ios.boardcrud.repository.PostRepository;
import pong.ios.boardcrud.repository.UserRepository;

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
            return new PostResponse(post, likeRepository.countByPost(post));
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

    public void likePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        UserEntity user = userRepository.findByUsername(username);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (!likeRepository.existsByLikerAndPost(user, post)) {
            LikeId likeId = new LikeId(post.getId(), user.getId());
            likeRepository.save(new Like(likeId, user, post));
        }

    }

    public void unlikePost(Long id, String username) throws NoSuchElementException {

        Post post = postRepository.findPostById(id);
        UserEntity user = userRepository.findByUsername(username);

        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        if (likeRepository.existsByLikerAndPost(user, post)) {
            likeRepository.deleteByLikerAndPost(user, post);
        }

    }

    public int countPostLike(Long postId) throws NoSuchElementException {
        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }

        return likeRepository.countByPost(post);
    }

}
