package pong.ios.boardcrud.adapter.out.persistence.post.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.global.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {
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
    private String category;

    @ElementCollection
    @CollectionTable(
            name = "tb_post_tags",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "tag", nullable = false)
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(nullable = false)
    private boolean isPinned;

    private LocalDateTime pinnedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pinned_by_user_id") // This can be null
    private UserEntity pinnedBy;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int commentCount;

    @Column
    private LocalDateTime deletedAt;

    public void update(
            String title,
            String content,
            String category,
            List<String> tags,
            PostStatus status,
            boolean isPinned,
            LocalDateTime pinnedAt,
            UserEntity pinnedBy,
            int viewCount,
            int likeCount,
            int commentCount,
            LocalDateTime deletedAt
    ) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = new ArrayList<>(tags);
        this.status = status;
        this.isPinned = isPinned;
        this.pinnedAt = pinnedAt;
        this.pinnedBy = pinnedBy;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.deletedAt = deletedAt;
    }
}
