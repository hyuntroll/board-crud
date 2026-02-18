package pong.ios.boardcrud.adapter.out.persistence.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.mapper.BoardManagerMapper;
import pong.ios.boardcrud.adapter.out.persistence.board.repository.BoardManagerRepository;
import pong.ios.boardcrud.application.port.out.board.DeleteBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardManagerPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardManager;
import pong.ios.boardcrud.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardManagerPersistenceAdapter implements
        SaveBoardManagerPort,
        LoadBoardManagerPort,
        DeleteBoardManagerPort {
    private final BoardManagerRepository boardManagerRepository;
    private final BoardManagerMapper boardManagerMapper;

    @Override
    public Optional<BoardManager> findById(Long boardId, Long userId) {
        return boardManagerRepository.findByBoard_IdAndUser_Id(boardId, userId)
                .map(boardManagerMapper::toDomain);
    }

    @Override
    public List<BoardManager> findAll(Long boardId) {
        return boardManagerRepository.findAllByBoard_Id(boardId).stream()
                .map(boardManagerMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(Long boardId, Long userId) {
        return boardManagerRepository.existsByBoard_IdAndUser_Id(boardId, userId);
    }

    @Override
    public Long save(Board board, User user, User granter) {
        return boardManagerRepository.save(
                boardManagerMapper.toEntity(
                        BoardManager.builder()
                                .board(board)
                                .user(user)
                                .grantedBy(granter)
                                .grantedAt(LocalDateTime.now())
                                .build()
                )
        ).getId();
    }

    @Override
    public Long save(Long boardId, Long userId) {
        Board board = Board.builder().id(boardId).build();
        User user = User.builder().id(userId).build();
        return boardManagerRepository.save(
                boardManagerMapper.toEntity(
                        BoardManager.builder()
                                .board(board)
                                .user(user)
                                .grantedBy(user)
                                .grantedAt(LocalDateTime.now())
                                .build()
                )
        ).getId();
    }

    @Override
    public void delete(Long boardId, Long userId) {
        boardManagerRepository.deleteByBoard_IdAndUser_Id(boardId, userId);
    }
}
