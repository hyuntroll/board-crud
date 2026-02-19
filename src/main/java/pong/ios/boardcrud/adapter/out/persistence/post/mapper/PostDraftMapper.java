package pong.ios.boardcrud.adapter.out.persistence.post.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostDraftEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.post.PostDraft;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.global.mapper.Mapper;

@Component
public class PostDraftMapper implements Mapper<PostDraft, PostDraftEntity> {
    @Override
    public PostDraftEntity toEntity(PostDraft domain) {
        return PostDraftEntity.builder()
                .id(domain.getId())
                .user(toUserRef(domain.getUser().getId()))
                .board(toBoardRef(domain.getBoard().getId()))
                .title(domain.getTitle())
                .content(domain.getContent())
                .savedAt(domain.getSavedAt())
                .build();
    }

    public PostDraftEntity toEntity(PostDraft domain, UserEntity userEntity, BoardEntity boardEntity) {
        return PostDraftEntity.builder()
                .id(domain.getId())
                .user(userEntity)
                .board(boardEntity)
                .title(domain.getTitle())
                .content(domain.getContent())
                .savedAt(domain.getSavedAt())
                .build();
    }

    @Override
    public PostDraft toDomain(PostDraftEntity entity) {
        return PostDraft.builder()
                .id(entity.getId())
                .user(User.builder().id(entity.getUser().getId()).build())
                .board(Board.builder().id(entity.getBoard().getId()).build())
                .title(entity.getTitle())
                .content(entity.getContent())
                .savedAt(entity.getSavedAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private UserEntity toUserRef(Long id) {
        return UserEntity.builder().id(id).build();
    }

    private BoardEntity toBoardRef(Long id) {
        return BoardEntity.builder().id(id).build();
    }
}
