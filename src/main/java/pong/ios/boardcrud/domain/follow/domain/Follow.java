package pong.ios.boardcrud.domain.follow.domain;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.domain.user.domain.UserEntity;

import java.io.Serializable;

@Entity(name="follow")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow implements Serializable {

    @EmbeddedId
    private FollowId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private UserEntity following;

    public Follow(FollowId id, UserEntity follower, UserEntity following) {
        this.id = id;
        this.follower = follower;
        this.following = following;
    }
}
