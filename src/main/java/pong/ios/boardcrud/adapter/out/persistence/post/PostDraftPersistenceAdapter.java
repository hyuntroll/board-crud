package pong.ios.boardcrud.adapter.out.persistence.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.post.mapper.PostDraftMapper;
import pong.ios.boardcrud.adapter.out.persistence.post.repository.PostDraftRepository;
import pong.ios.boardcrud.application.port.out.postdraft.DeletePostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.LoadPostDraftPort;
import pong.ios.boardcrud.application.port.out.postdraft.SavePostDraftPort;
import pong.ios.boardcrud.domain.post.PostDraft;

import java.util.List;
import java.util.Optional;

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
    public List<PostDraft> findAllByUserId(Long userId) {
        return postDraftRepository.findAllByUser_Id(userId).stream()
                .map(postDraftMapper::toDomain)
                .toList();
    }

    @Override
    public List<PostDraft> findAllByUserIdAndBoardId(Long userId, Long boardId) {
        return postDraftRepository.findAllByUser_IdAndBoard_Id(userId, boardId).stream()
                .map(postDraftMapper::toDomain)
                .toList();
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
}
