package pong.ios.boardcrud.domain.entity.like;


import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.domain.entity.post.Post;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

import java.io.Serializable;

@Entity
@Table(name = "like")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like implements Serializable {

    @EmbeddedId
    private LikeId id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("likerId")
    @JoinColumn(name = "liker_id")
    private UserEntity writer;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    public Like(LikeId id, UserEntity writer, Post post) {
        this.id = id;
        this.writer = writer;
        this.post = post;
    }

}
