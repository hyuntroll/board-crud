package pong.ios.boardcrud.adapter.out.persistence.board.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardManagerEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.mapper.UserMapper;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardManager;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.global.mapper.Mapper;

import java.time.LocalDateTime;

@Component
public class BoardManagerMapper implements Mapper<BoardManager, BoardManagerEntity> {
    private final UserMapper userMapper;

    public BoardManagerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public BoardManagerEntity toEntity(BoardManager domain) {
        return BoardManagerEntity.builder()
                .id(domain.getId())
                .board(toBoardRef(domain.getBoard().getId()))
                .user(toUserRef(domain.getUser().getId()))
                .granter(toUserRef(domain.getGrantedBy().getId()))
                .grantedAt(domain.getGrantedAt() != null ? domain.getGrantedAt() : LocalDateTime.now())
                .build();
    }

    public BoardManagerEntity toEntity(BoardEntity boardEntity, UserEntity userEntity, UserEntity granterEntity) {
        return BoardManagerEntity.builder()
                .board(boardEntity)
                .user(userEntity)
                .granter(granterEntity)
                .grantedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public BoardManager toDomain(BoardManagerEntity entity) {
        return BoardManager.builder()
                .id(entity.getId())
                .board(Board.builder().id(entity.getBoard().getId()).build())
                .user(userMapper.toDomain(entity.getUser()))
                .grantedAt(entity.getGrantedAt())
                .grantedBy(User.builder().id(entity.getGranter().getId()).build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private BoardEntity toBoardRef(Long boardId) {
        return BoardEntity.builder()
                .id(boardId)
                .build();
    }

    private UserEntity toUserRef(Long userId) {
        return UserEntity.builder()
                .id(userId)
                .build();
    }
}
