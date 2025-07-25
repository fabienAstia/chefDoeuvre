package co.simplon.personalities.mappers;

import co.simplon.personalities.dtos.OffersResponse;
import co.simplon.personalities.dtos.CoordinatesView;
import co.simplon.personalities.dtos.OfferJobView;
import co.simplon.personalities.dtos.francetravail.FranceTravailResponse;
import co.simplon.personalities.dtos.francetravail.OfferJob;

import java.util.List;

import static java.util.Objects.nonNull;

public class OffersResponseMapper {

    public static OffersResponse mapToOffersResponse(FranceTravailResponse franceTravailResponse) {
        List<OfferJob> offerJobList = franceTravailResponse.offerJobList();
        System.out.println("offerJobList" + offerJobList);
        return new OffersResponse(
                mapToOfferJobView(offerJobList),
                mapToCoordinatesView(offerJobList)
        );
    }

    public static List<OfferJobView> mapToOfferJobView(List<OfferJob> offerJobList) {
        return offerJobList.stream().map(offerJob -> {
            System.out.println("offerJobCoordinates" + offerJob.coordinates());
            return new OfferJobView(
                    offerJob.title(),
                    offerJob.contractType(),
                    offerJob.experience(),
                    offerJob.description(),
                    offerJob.company().companyName(),
                    new CoordinatesView(
                            offerJob.coordinates().longitude(),
                            offerJob.coordinates().latitude()
                    ),
                    nonNull(offerJob.workingContext().workingHours()) ? offerJob.workingContext().workingHours().getFirst() : "not found",
                    offerJob.salary().salary(),
                    offerJob.offerOrigin().sourceUrl()
            );
        }).toList();
    }

    public static List<CoordinatesView> mapToCoordinatesView(List<OfferJob> offerJobList) {
        return offerJobList.stream().map(offerJob -> {
            return new CoordinatesView(
                    offerJob.coordinates().longitude(),
                    offerJob.coordinates().latitude()
            );
        }).toList();
    }
}
