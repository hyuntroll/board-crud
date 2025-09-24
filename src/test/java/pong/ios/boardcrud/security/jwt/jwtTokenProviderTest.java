package pong.ios.boardcrud.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class jwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void testAccessTokenProvide() {
        String token = jwtTokenProvider.provideAccessToken("username", "ROLE_USER");

        System.out.println(token); // assertThat(val, is(real_value))
    }

    @Test
    public void testRefreshTokenProvide() {
        String token = jwtTokenProvider.provideRefreshToken("username", "ROLE_USER");

        System.out.println(token);
    }

}