package pong.ios.boardcrud.adapter.in.rest.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.domain.post.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "게시글 상세 응답")
public record PostResponse(
        @Schema(description = "게시글 ID", example = "10")
        Long id,
        @Schema(description = "작성자 ID", example = "1")
        Long userId,
        @Schema(description = "작성자 닉네임", example = "hyun")
        String writerNickname,
        @Schema(description = "작성자 프로필")
        String writerProfile,
        @Schema(description = "게시판 ID", example = "1")
        Long boardId,
        @Schema(description = "제목")
        String title,
        @Schema(description = "내용")
        String content,
        @Schema(description = "카테고리")
        String category,
        @Schema(description = "태그 목록")
        List<String> tags,
        @Schema(description = "게시글 상태")
        PostStatus status,
        @Schema(description = "공개 여부", example = "true")
        boolean isPublic,
        @Schema(description = "댓글 허용 여부", example = "true")
        boolean isCommentAllowed,
        @Schema(description = "블라인드 여부", example = "false")
        boolean isBlinded,
        @Schema(description = "조회수", example = "0")
        int viewCount,
        @Schema(description = "좋아요 수", example = "0")
        int likeCount,
        @Schema(description = "댓글 수", example = "0")
        int commentCount,
        @Schema(description = "삭제 시각")
        LocalDateTime deletedAt,
        @Schema(description = "생성 시각")
        LocalDateTime createdAt,
        @Schema(description = "수정 시각")
        LocalDateTime updatedAt
) {
    public static PostResponse from(PostResult result) {
        return new PostResponse(
                result.id(),
                result.userId(),
                result.writerNickname(),
                result.writerProfile(),
                result.boardId(),
                result.title(),
                result.content(),
                result.category(),
                result.tags(),
                result.status(),
                result.isPublic(),
                result.isCommentAllowed(),
                result.isBlinded(),
                result.viewCount(),
                result.likeCount(),
                result.commentCount(),
                result.deletedAt(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
