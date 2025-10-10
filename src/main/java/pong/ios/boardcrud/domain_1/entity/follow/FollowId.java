package pong.ios.boardcrud.domain_1.entity.follow;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FollowId implements Serializable {

    private Long followerId;
    private Long followingId;

    @Override
    public boolean equals(Object o ) {
        if (this == o) return true;
        if (!(o instanceof FollowId that)) return false;
        return Objects.equals(followerId, that.followerId) &&
                Objects.equals(followingId, that.followingId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followingId);
    }
}
