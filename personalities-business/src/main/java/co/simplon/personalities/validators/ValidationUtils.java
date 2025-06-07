package co.simplon.personalities.validators;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

final class ValidationUtils {

    private ValidationUtils() {
    }

    @SuppressWarnings("unchecked")
    static String pathVariableString(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return pathVariables.get(name);
    }

    static Long pathVariableAsLong(String name) {
        return Long.valueOf(pathVariableString(name));
    }
}
