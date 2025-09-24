package pong.ios.boardcrud.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;


    @Test
    public void testTokenExpired() {
        String test = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IuuLiOyWvOq1tCIsImVtYWlsIjoiaHNtMjAwOTA1MjkyMUBkZ3N3LmhzLmtyMSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTgxMDY4MTUsImV4cCI6MTc1ODEwNjg1MX0.qfzS0fWphyIG7mtXEs8ziBaU2B7TgLBQZq7YZ9qSXZE";

        assertTrue(jwtUtil.isExpired(test));
    }

    @Test
    public void testExtractClaims() {
        String test = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IuuLiOyWvOq1tCIsImVtYWlsIjoiaHNtMjAwOTA1MjkyMUBkZ3N3LmhzLmtyMSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3NTgxMDY4MTUsImV4cCI6MTc1ODEwNjg1MX0.qfzS0fWphyIG7mtXEs8ziBaU2B7TgLBQZq7YZ9qSXZE";

        jwtUtil.extractClaims(test);
    }

}