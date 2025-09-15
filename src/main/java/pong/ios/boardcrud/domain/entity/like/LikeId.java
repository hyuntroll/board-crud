package pong.ios.boardcrud.domain.entity.like;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LikeId implements Serializable {

    private Long writerId;

    private Long postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId that)) return false;
        return Objects.equals(writerId, that.writerId ) && Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writerId, postId);
    }
}
