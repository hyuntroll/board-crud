package pong.ios.boardcrud.domain.entity.refresh;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Getter
@Setter
@RedisHash( value = "refresh", timeToLive = 86400000L)
public class RefreshEntity {

    @Id
    private Long id;

    private String username;

    private String refresh;

    private Date expiration;

}
