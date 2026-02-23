package pong.ios.boardcrud.adapter.out.persistence.like.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.adapter.out.persistence.comment.entity.CommentEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_comment_likes",
        uniqueConstraints = @UniqueConstraint(name = "uk_comment_like_comment_user", columnNames = {"comment_id", "user_id"})
)
public class CommentLikeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
