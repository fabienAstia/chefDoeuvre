package com.personalities.dtos.poleemploi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OfferJob(
        @JsonProperty("appellationlibelle") String title,
        @JsonProperty("typeContrat") String contractType,
        @JsonProperty("experienceLibelle") String experience,
        @JsonProperty("description") String description,
        @JsonProperty("entreprise") Company company,
        @JsonProperty("lieuTravail") Coordinates coordinates,
        @JsonProperty("contexteTravail") WorkingContext workingContext,
        @JsonProperty("salaire") Salary salary,
        @JsonProperty("origineOffre") OfferOrigin offerOrigin
) {
}
