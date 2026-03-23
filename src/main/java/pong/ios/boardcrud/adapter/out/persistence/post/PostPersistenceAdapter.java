package pong.ios.boardcrud.adapter.out.persistence.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.post.mapper.PostMapper;
import pong.ios.boardcrud.adapter.out.persistence.post.repository.PostRepository;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.util.PageableUtils;

import java.util.Set;
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
    public PageResult<Post> findAll(PageQuery query) {
        Page<Post> page = postRepository.findAllByStatusAndIsPublicTrueAndIsBlindedFalse(
                        PostStatus.PUBLISHED,
                        toPageable(query)
                )
                .map(postMapper::toDomain);

        return PageResult.from(page);
    }

    @Override
    public PageResult<Post> findAllByBoardId(Long boardId, PageQuery query) {
        Page<Post> page = postRepository.findAllByBoard_IdAndStatusAndIsPublicTrueAndIsBlindedFalse(
                        boardId,
                        PostStatus.PUBLISHED,
                        toPageable(query)
                )
                .map(postMapper::toDomain);

        return PageResult.from(page);
    }

    @Override
    public Post save(Post post) {
        return postMapper.toDomain(
                postRepository.save(
                        postMapper.toEntity(post)
                )
        );
    }

    private Pageable toPageable(PageQuery query) {
        Set<String> sortableFields = Set.of("createdAt", "updatedAt", "viewCount", "likeCount", "commentCount");
        Pageable pageable = PageableUtils.toPageable(query, sortableFields, "createdAt");
        Sort sort = Sort.by(Sort.Direction.DESC, "isPinned").and(pageable.getSort());
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
