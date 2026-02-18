package pong.ios.boardcrud.adapter.in.rest.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "아이디 또는 이메일을 입력하세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요.")
        String password
) {}
