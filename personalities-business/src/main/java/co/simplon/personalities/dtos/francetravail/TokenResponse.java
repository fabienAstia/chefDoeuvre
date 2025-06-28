package co.simplon.personalities.dtos.francetravail;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

public record TokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("scope") String scope,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Integer expiresIn
) {
    public boolean isValid() {
        return StringUtils.hasText(accessToken);
    }
}
