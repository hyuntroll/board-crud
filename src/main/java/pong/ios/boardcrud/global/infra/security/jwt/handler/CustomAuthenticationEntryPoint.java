package pong.ios.boardcrud.global.infra.security.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.global.exception.ErrorResponse;
import pong.ios.boardcrud.global.util.ApiResponseWriter;

import java.io.IOException;

import static pong.ios.boardcrud.global.exception.CommonStatusCode.UNAUTHORIZED;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApiResponseWriter writer;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = UNAUTHORIZED.getHttpStatus();
        ErrorResponse errorResponse = ErrorResponse.of(
                UNAUTHORIZED.getHttpStatus().value(),
                UNAUTHORIZED.name(),
                UNAUTHORIZED.getMessage()
        );

        writer.write(status, errorResponse, response);

    }
}
