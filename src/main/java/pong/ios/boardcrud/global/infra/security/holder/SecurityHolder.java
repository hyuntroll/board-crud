package pong.ios.boardcrud.global.infra.security.holder;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.exception.CommonStatusCode;

@Component
public class SecurityHolder {

    public Long getCurrentUserId() {
        return (Long) getAuthentication().getPrincipal();
    }

    public boolean isAuthenticated() {
        Authentication auth = getAuthentication();
        return auth != null && auth.isAuthenticated();
    }

    public Authentication getAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            throw new ApplicationException(CommonStatusCode.UNAUTHORIZED);
        }

        return auth;
    }
}
