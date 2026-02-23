package pong.ios.boardcrud.adapter.in.rest.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardManager;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "게시판 응답")
public record BoardResponse(
        @Schema(description = "게시판 ID", example = "1")
        Long id,
        @Schema(description = "게시판 이름", example = "자유게시판")
        String name,
        @Schema(description = "게시판 타입", example = "FREE")
        String type,
        @Schema(description = "게시판 설명")
        String description,
        @Schema(description = "활성 여부", example = "true")
        boolean active,
        @Schema(description = "관리자 수", example = "2")
        int managerCount,
        @Schema(description = "관리자 목록")
        List<BoardManagerSummary> managers,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt,
        @Schema(description = "수정 시각")
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

    @Schema(description = "게시판 관리자 요약")
    public record BoardManagerSummary(
            @Schema(description = "유저 ID", example = "2")
            Long userId,
            @Schema(description = "아이디", example = "manager")
            String username,
            @Schema(description = "닉네임", example = "매니저")
            String nickname,
            @Schema(description = "프로필 경로")
            String profile,
            @Schema(description = "권한 부여 시각")
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
