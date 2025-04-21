package com.personalities.dtos.poleemploi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PoleEmploiResponse(
        @JsonProperty("resultats") List<OfferJob> offerJobList
) {
}
