package pong.ios.boardcrud.adapter.out.persistence.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.post.mapper.PostDraftMapper;
import pong.ios.boardcrud.adapter.out.persistence.post.repository.PostDraftRepository;
import pong.ios.boardcrud.application.port.out.postdraft.DeletePostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.LoadPostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.SavePostDraftPort;
import pong.ios.boardcrud.domain.post.PostDraft;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.util.PageableUtils;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostDraftPersistenceAdapter implements
        SavePostDraftPort,
        LoadPostDraftPort,
        DeletePostDraftPort {
    private final PostDraftRepository postDraftRepository;
    private final PostDraftMapper postDraftMapper;

    @Override
    public Optional<PostDraft> findById(Long draftId) {
        return postDraftRepository.findById(draftId)
                .map(postDraftMapper::toDomain);
    }

    @Override
    public PageResult<PostDraft> findAllByUserId(Long userId, PageQuery query) {
        Page<PostDraft> page = postDraftRepository.findAllByUser_Id(userId, toPageable(query))
                .map(postDraftMapper::toDomain);
        return PageResult.from(page);
    }

    @Override
    public PageResult<PostDraft> findAllByUserIdAndBoardId(Long userId, Long boardId, PageQuery query) {
        Page<PostDraft> page = postDraftRepository.findAllByUser_IdAndBoard_Id(userId, boardId, toPageable(query))
                .map(postDraftMapper::toDomain);
        return PageResult.from(page);
    }

    @Override
    public PostDraft save(PostDraft postDraft) {
        return postDraftMapper.toDomain(
                postDraftRepository.save(
                        postDraftMapper.toEntity(postDraft)
                )
        );
    }

    @Override
    public void delete(Long draftId) {
        postDraftRepository.deleteById(draftId);
    }

    private Pageable toPageable(PageQuery query) {
        Set<String> sortableFields = Set.of("savedAt", "createdAt", "updatedAt");
        return PageableUtils.toPageable(query, sortableFields, "savedAt");
    }
}
