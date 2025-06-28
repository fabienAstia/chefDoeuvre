package co.simplon.personalities.dtos.francetravail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Company(
        @JsonProperty("nom") String companyName
) {
}
