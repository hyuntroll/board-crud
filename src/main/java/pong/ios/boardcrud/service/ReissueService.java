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
import pong.ios.boardcrud.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;


import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;

    private final RedisService redisService;

    @Value("${spring.jwt.expired-ms.refresh}")
    private Long refreshExpiredMs;

    @Value("${spring.jwt.expired-ms.access}")
    private Long accessExpiredMs;


    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        Cookie[] cookies = request.getCookies();
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

        String access = jwtUtil.createJwt("access", username, role, accessExpiredMs);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, refreshExpiredMs);

        redisService.deleteToken(refresh);
        addRefreshEntity(username, newRefresh, refreshExpiredMs);

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


