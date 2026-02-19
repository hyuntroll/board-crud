package pong.ios.boardcrud.adapter.out.persistence.board.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.global.mapper.Mapper;

@Component
public class BoardMapper implements Mapper<Board, BoardEntity> {

    @Override
    public BoardEntity toEntity(Board domain) {
        return BoardEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .type(domain.getType())
                .description(domain.getDescription())
                .isActive(domain.isActive())
                .build();
    }

    @Override
    public Board toDomain(BoardEntity entity) {
        return Board.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
