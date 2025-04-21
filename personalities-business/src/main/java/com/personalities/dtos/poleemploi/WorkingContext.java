package com.personalities.dtos.poleemploi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WorkingContext(
        @JsonProperty("horaires") List<String> workingHours
) {
}
