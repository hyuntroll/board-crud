package pong.ios.boardcrud.global.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash( value = "refresh", timeToLive = 10 )
@AllArgsConstructor
public class RefreshToken {

    private String username;

    private String refreshToken;

    private Long expireMs;

    public RefreshToken(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }


}
