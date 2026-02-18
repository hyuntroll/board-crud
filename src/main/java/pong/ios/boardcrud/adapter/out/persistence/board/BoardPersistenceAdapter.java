package pong.ios.boardcrud.adapter.out.persistence.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.board.mapper.BoardMapper;
import pong.ios.boardcrud.adapter.out.persistence.board.repository.BoardRepository;
import pong.ios.boardcrud.application.port.out.board.LoadBoardPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardPersistenceAdapter implements LoadBoardPort, SaveBoardPort {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id)
                .map(boardMapper::toDomain);
    }

    @Override
    public Board save(Board board) {
        if (board.getId() == null) {
            BoardEntity created = boardRepository.save(boardMapper.toEntity(board));
            return boardMapper.toDomain(created);
        }

        BoardEntity boardEntity = boardRepository.findById(board.getId())
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));

        boardEntity.update(
                board.getName(),
                board.getType(),
                board.getDescription(),
                board.isActive()
        );

        return boardMapper.toDomain(boardRepository.save(boardEntity));
    }
}
