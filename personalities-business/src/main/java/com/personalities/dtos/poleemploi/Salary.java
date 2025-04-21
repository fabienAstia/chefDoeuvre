package com.personalities.dtos.poleemploi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Salary(
        @JsonProperty("libelle") String salary
) {
}
