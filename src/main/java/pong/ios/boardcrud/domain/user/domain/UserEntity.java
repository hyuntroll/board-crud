package pong.ios.boardcrud.domain.user.domain;


import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.domain_1.entity.comment.Comment;
import pong.ios.boardcrud.domain.like.domain.Like;
import pong.ios.boardcrud.domain.post.domain.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users_1")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String role;

    @Column(name="u_email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "commenter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "liker", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<UserEntity> following = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "following")
    private List<UserEntity> followers = new ArrayList<>();


    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

}
