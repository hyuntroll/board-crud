package pong.ios.boardcrud.domain.entity.comment;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

@Entity
@Table(name="comment")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user")
    private UserEntity commenter;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post")
    private Post post;

    @Column
    private String title;

    @Column
    private String content;

    public Comment(String title, String content, UserEntity commenter, Post post) {
        this.title = title;
        this.content = content;
        this.commenter = commenter;
        this.post = post;
    }
}
