package pong.ios.boardcrud.adapter.in.rest.board.dto.response;

import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardManager;

import java.time.LocalDateTime;
import java.util.List;

public record BoardResponse(
        Long id,
        String name,
        String type,
        String description,
        boolean active,
        int managerCount,
        List<BoardManagerSummary> managers,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BoardResponse from(Board board, List<BoardManager> managers) {
        return new BoardResponse(
                board.getId(),
                board.getName(),
                board.getType(),
                board.getDescription(),
                board.isActive(),
                managers.size(),
                managers.stream()
                        .map(BoardManagerSummary::from)
                        .toList(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }

    public record BoardManagerSummary(
            Long userId,
            String username,
            String nickname,
            String profile,
            LocalDateTime grantedAt
    ) {
        public static BoardManagerSummary from(BoardManager boardManager) {
            return new BoardManagerSummary(
                    boardManager.getUser().getId(),
                    boardManager.getUser().getUsername(),
                    boardManager.getUser().getNickname(),
                    boardManager.getUser().getProfile(),
                    boardManager.getGrantedAt()
            );
        }
    }
}
