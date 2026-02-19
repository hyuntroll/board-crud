package pong.ios.boardcrud.adapter.out.persistence.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.post.mapper.PostMapper;
import pong.ios.boardcrud.adapter.out.persistence.post.repository.PostRepository;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostStatus;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements LoadPostPort, SavePostPort {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::toDomain);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAllByStatusOrderByCreatedAtDesc(PostStatus.PUBLISHED).stream()
                .map(postMapper::toDomain)
                .toList();
    }

    @Override
    public List<Post> findAllByBoardId(Long boardId) {
        return postRepository.findAllByBoard_IdAndStatusOrderByCreatedAtDesc(boardId, PostStatus.PUBLISHED).stream()
                .map(postMapper::toDomain)
                .toList();
    }

    @Override
    public Post save(Post post) {
        return postMapper.toDomain(
                postRepository.save(
                        postMapper.toEntity(post)
                )
        );
    }
}
