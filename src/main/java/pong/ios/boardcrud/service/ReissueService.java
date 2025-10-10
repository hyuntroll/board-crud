package pong.ios.boardcrud.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pong.ios.boardcrud.security.jwt.JwtUtil;
import pong.ios.boardcrud.util.CookieUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtUtil jwtUtil;

    private final RedisService redisService;

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

        String access = jwtUtil.createJwt("access", username, role, jwtUtil.accessExpiration);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, jwtUtil.refreshExpiration);

        redisService.deleteToken(refresh);
        redisService.saveToken(username, newRefresh, jwtUtil.refreshExpiration);

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", access);
        map.put("token_type", "Bearer");
        map.put("expire_in", jwtUtil.accessExpiration / 1000);
        CookieUtil.createCookie(response, "refresh", newRefresh, 60 * 60 * 24 * 3600);

        objectMapper.writeValue(response.getWriter(), map);


        return ResponseEntity.ok().build();
    }


}


