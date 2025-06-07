package co.simplon.personalities.dtos;

import java.util.List;

public record PaginatedOffersResponse(
        List<OfferJobView> paginatedOfferJobViews,
        List<CoordinatesView> allCoordinates
) {
}
