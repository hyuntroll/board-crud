package pong.ios.boardcrud.domain.entity.post;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.user.BoardUser;

@Entity(name="post")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "p_title", nullable = false)
    private String title;

    @Column(name="p_content", nullable = false)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="writer")
    private BoardUser writer;

    public Post(String title, String content, BoardUser writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}