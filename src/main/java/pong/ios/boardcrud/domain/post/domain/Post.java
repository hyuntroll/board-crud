package pong.ios.boardcrud.domain.post.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pong.ios.boardcrud.domain_1.entity.comment.Comment;
import pong.ios.boardcrud.domain_1.entity.like.Like;
import pong.ios.boardcrud.domain.user.domain.UserEntity;

import java.util.ArrayList;
import java.util.List;

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

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="writer")
    private UserEntity writer;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Post(String title, String content, UserEntity writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }


    public void addComment(Comment comment) {
        comments.add(comment);
    }

}