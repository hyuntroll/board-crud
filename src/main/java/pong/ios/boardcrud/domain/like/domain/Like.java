package pong.ios.boardcrud.domain.like.domain;


import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.domain.post.domain.Post;
import pong.ios.boardcrud.domain.user.domain.UserEntity;

import java.io.Serializable;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like implements Serializable {

    @EmbeddedId
    private LikeId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("likerId")
    @JoinColumn(name = "liker_id")
    private UserEntity liker;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    public Like(LikeId id, UserEntity user, Post post) {

        this.id = id;

        this.liker = user;

        this.post = post;

    }

}
