package pong.ios.boardcrud.global.infra.security.jwt.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.global.error.ErrorResponse;
import pong.ios.boardcrud.global.util.ApiResponseWriter;

import java.io.IOException;

import static pong.ios.boardcrud.global.error.CommonStatusCode.ACCESS_DENIED;


@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ApiResponseWriter writer;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpStatus status = ACCESS_DENIED.getHttpStatus();
        ErrorResponse errorResponse = ErrorResponse.of(
                ACCESS_DENIED.getHttpStatus().value(),
                ACCESS_DENIED.name(),
                ACCESS_DENIED.getMessage());

        writer.write(status, errorResponse, response);

    }
}
