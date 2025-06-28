package co.simplon.personalities.dtos.francetravail;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FranceTravailResponse(
        @JsonProperty("resultats") List<OfferJob> offerJobList
) {
}
