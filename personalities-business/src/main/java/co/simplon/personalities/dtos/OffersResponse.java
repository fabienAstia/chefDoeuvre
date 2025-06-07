package co.simplon.personalities.dtos;

import java.util.List;

public record OffersResponse(
        List<OfferJobView> offerJobViews,
        List<CoordinatesView> allCoordinates
) {
}
