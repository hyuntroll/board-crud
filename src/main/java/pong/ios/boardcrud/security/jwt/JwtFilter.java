package pong.ios.boardcrud.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pong.ios.boardcrud.domain.user.domain.UserEntity;
import pong.ios.boardcrud.global.auth.domain.CustomUserDetails;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 에서 Authorization 헤더를 찾음
        String accessToken = request.getHeader("Authorization");
        String token = resolveToken(accessToken);

        // Authorization 헤더 검증 및 토큰 만료 확인 여부
        if (!validateToken(token))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 username과 role
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // userEntity를 생성하여 설정
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .role(role)
                .password("null")
                .build();

        // UserDetails 에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

    private String resolveToken(String accessToken) {
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return null;
        }

        return accessToken.substring(7);
    }

    private boolean validateToken(String accessToken) {
        if (accessToken == null) {
            return false;
        }

        return jwtUtil.validateToken(accessToken);
    }
}
