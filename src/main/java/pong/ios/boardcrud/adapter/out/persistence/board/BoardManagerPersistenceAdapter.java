package pong.ios.boardcrud.adapter.out.persistence.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardManagerEntity;
import pong.ios.boardcrud.adapter.out.persistence.board.mapper.BoardManagerMapper;
import pong.ios.boardcrud.adapter.out.persistence.board.repository.BoardManagerRepository;
import pong.ios.boardcrud.adapter.out.persistence.board.repository.BoardRepository;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.repository.UserRepository;
import pong.ios.boardcrud.application.port.out.board.DeleteBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.SaveBoardManagerPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardManager;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardManagerPersistenceAdapter implements
        SaveBoardManagerPort,
        LoadBoardManagerPort,
        DeleteBoardManagerPort {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
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
        BoardEntity boardEntity = loadBoardEntity(board.getId());
        UserEntity userEntity = loadUserEntity(user.getId());
        UserEntity granterEntity = loadUserEntity(granter.getId());

        BoardManagerEntity saved = boardManagerRepository.save(
                boardManagerMapper.toEntity(boardEntity, userEntity, granterEntity)
        );
        return saved.getId();
    }

    @Override
    public Long save(Long boardId, Long userId) {
        BoardEntity boardEntity = loadBoardEntity(boardId);
        UserEntity userEntity = loadUserEntity(userId);

        BoardManagerEntity saved = boardManagerRepository.save(
                boardManagerMapper.toEntity(boardEntity, userEntity, userEntity)
        );
        return saved.getId();
    }

    @Override
    public void delete(Long boardId, Long userId) {
        boardManagerRepository.deleteByBoard_IdAndUser_Id(boardId, userId);
    }

    private BoardEntity loadBoardEntity(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
    }

    private UserEntity loadUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
    }
}
