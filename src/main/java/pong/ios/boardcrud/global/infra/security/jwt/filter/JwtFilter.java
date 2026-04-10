package pong.ios.boardcrud.global.infra.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pong.ios.boardcrud.global.infra.security.jwt.JwtExtract;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtExtract jwtExtract;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtExtract.extractTokenFromRequest(request);
        if (token != null) {
            SecurityContextHolder.getContext().setAuthentication(jwtExtract.getAuthentication(token));
        }
        filterChain.doFilter(request, response);
    }

}

