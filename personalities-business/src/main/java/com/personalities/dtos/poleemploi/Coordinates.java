package com.personalities.dtos.poleemploi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Coordinates(
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
) {
}
