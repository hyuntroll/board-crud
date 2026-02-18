package pong.ios.boardcrud.adapter.out.persistence.post.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_post_drafts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDraftEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime savedAt;

    public void update(BoardEntity board, String title, String content, LocalDateTime savedAt) {
        this.board = board;
        this.title = title;
        this.content = content;
        this.savedAt = savedAt;
    }
}
