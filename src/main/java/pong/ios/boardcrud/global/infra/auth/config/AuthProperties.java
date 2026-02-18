package pong.ios.boardcrud.global.infra.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix="spring.auth")
public class AuthProperties {

    private int attemptWindow;

    private int lockDuration;
}
