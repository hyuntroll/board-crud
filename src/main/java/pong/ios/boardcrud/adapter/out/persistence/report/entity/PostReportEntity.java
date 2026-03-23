package pong.ios.boardcrud.adapter.out.persistence.report.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.domain.report.PostReportReason;
import pong.ios.boardcrud.domain.report.PostReportStatus;
import pong.ios.boardcrud.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_post_reports",
        uniqueConstraints = @UniqueConstraint(name = "uk_post_report_post_reporter", columnNames = {"post_id", "reporter_user_id"})
)
public class PostReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private UserEntity reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostReportReason reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostReportStatus status;

    public void update(PostReportStatus status) {
        this.status = status;
    }
}
