package pong.ios.boardcrud.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.global.data.BaseResponse;
import pong.ios.boardcrud.global.error.ErrorResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiResponseWriter {

    private final ObjectMapper mapper;

    public void write(HttpStatus status, BaseResponse baseResponse, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        String json = mapper.writeValueAsString(baseResponse);
        response.getWriter().write(json);
    }

    public void write(HttpStatus status, ErrorResponse errorResponse, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        String json = mapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}
