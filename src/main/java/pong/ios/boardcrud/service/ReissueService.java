package pong.ios.boardcrud.service;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.domain.entity.refresh.RefreshToken;
import pong.ios.boardcrud.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import pong.ios.boardcrud.security.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtUtil jwtUtil;

    private final RedisService redisService;

    @Value("${spring.jwt.expiration.refresh}")
    private Long refreshExpiredMs;

    @Value("${spring.jwt.expiration.access}")
    private Long accessExpiredMs;


    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }
        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh token is null");
        }

        try {
            jwtUtil.isExpired(refresh);
        }
        catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token is expired");
        }

        if (redisService.findByToken(refresh).isEmpty() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid refresh token");
        }


        String username  = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String access = jwtUtil.createJwt("access", username, role, 1200000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 18000000L);

        redisService.deleteToken(refresh);
        redisService.saveToken(username, newRefresh, 18000000L);

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh",  newRefresh));


        return ResponseEntity.ok().build();
    }

    public Cookie createCookie(String name, String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expireMs) {

        RefreshToken refreshEntity = new RefreshToken(username, refresh, expireMs);

        redisService.saveToken(refreshEntity);


    }

}


