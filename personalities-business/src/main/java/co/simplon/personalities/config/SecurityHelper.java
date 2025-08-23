package co.simplon.personalities.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

    public SecurityContext securityContext() {
        return SecurityContextHolder.getContext();
    }

    public Authentication authentication() {
        return securityContext().getAuthentication();
    }

    public String principal() {
        return authentication().getName();
    }
}
