package pong.ios.boardcrud.domain.entity.follow;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.user.BoardUser;

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
    private BoardUser follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private BoardUser following;
}
