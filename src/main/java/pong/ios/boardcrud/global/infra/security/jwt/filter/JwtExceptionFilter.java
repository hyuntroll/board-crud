package pong.ios.boardcrud.global.infra.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.exception.ErrorResponse;
import pong.ios.boardcrud.global.exception.StatusCode;
import pong.ios.boardcrud.global.util.ApiResponseWriter;

import java.io.IOException;

import static pong.ios.boardcrud.global.exception.CommonStatusCode.INTERNAL_SERVER_ERROR;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ApiResponseWriter writer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (ApplicationException e) {
            handleAuthException(e.getStatusCode().getHttpStatus(), response, e);
        }
        catch (Exception e) {
            handleAuthException(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    public void handleAuthException(
            HttpStatus status,
            HttpServletResponse response,
            ApplicationException ex
    ) throws IOException {
        StatusCode statusCode = ex.getStatusCode();

        ErrorResponse errorResponse = ErrorResponse.of(
                statusCode.getHttpStatus().value(),
                ex.getCode(),
                ex.getMessage() != null ? ex.getMessage() : statusCode.getMessage()
        );

        writer.write(status, errorResponse, response);
    }

    public void handleAuthException(
            HttpStatus status,
            HttpServletResponse response,
            Exception ex
    ) throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(
                INTERNAL_SERVER_ERROR.getHttpStatus().value(),
                INTERNAL_SERVER_ERROR.name(),
                ex.getMessage()
        );

        writer.write(status, errorResponse, response);
    }


}
