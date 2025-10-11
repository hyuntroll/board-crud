package pong.ios.boardcrud.global.security.auth.serivce;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.global.security.auth.jwt.JwtTokenProvider;
import pong.ios.boardcrud.global.security.auth.jwt.JwtUtil;
import pong.ios.boardcrud.global.redis.RedisService;
import pong.ios.boardcrud.global.util.CookieUtil;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refresh = CookieUtil.getCookie(request, "refresh");
        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh token is null");
        }

        try {
            jwtUtil.validateToken(refresh);
        }
        catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token is expired");
        }

        if (redisService.findByToken(refresh).isEmpty() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid refresh token");
        }

        String username  = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        jwtTokenProvider.accessAndRefreshTokenProvider(response, username, role);

        return ResponseEntity.ok().build();
    }


}


