package pong.ios.boardcrud.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pong.ios.boardcrud.domain.entity.user.UserEntity;
import pong.ios.boardcrud.dto.CustomUserDetails;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if ( authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("Can't find Bearer token");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간  검증
        if ( jwtUtil.isExpired(token) ) {

            log.info("token is expired");
            filterChain.doFilter(request, response);

            return;
        }

        // 토큰에서 username과 role
        String username = jwtUtil.getUsername(token);
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        // userEntity를 생성하여 설정
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
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
}
