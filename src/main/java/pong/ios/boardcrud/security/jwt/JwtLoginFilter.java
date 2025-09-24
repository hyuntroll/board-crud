package pong.ios.boardcrud.security.jwt;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pong.ios.boardcrud.service.RedisService;
import pong.ios.boardcrud.util.CookieUtil;

import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisService redisService;

    @Value("${spring.jwt.expiration.refresh}")
    private Long refreshExpiredMs;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");


        String access = jwtTokenProvider.provideAccessToken(username, role);
        String refresh = jwtTokenProvider.provideRefreshToken(username, role);

        redisService.saveToken(username, refresh, refreshExpiredMs);

        CookieUtil.createCookie(response, "refresh", refresh, 60 * 60 * 24 * 3600);

    }

}
