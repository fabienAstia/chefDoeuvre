package co.simplon.personalities.controllers;

import co.simplon.personalities.dtos.*;
import co.simplon.personalities.services.FranceTravailService;
//import co.simplon.personalities.services.PoleEmploiService;
import co.simplon.personalities.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class FranceTravailController {

    private final FranceTravailService franceTravailService;

    public FranceTravailController(FranceTravailService franceTravailService) {
        this.franceTravailService = franceTravailService;
    }

//    @GetMapping("/paginated")
//    public OffersResponse getOffersJobs(@RequestParam String motsCles) {
//        return franceTravailService.getSpecificJobs(motsCles);
//    }

    @GetMapping("/paginated")
    public ApiResponse<PaginatedOffersResponse> getPaginatedOfferJobs(
            @RequestParam String motsCles,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
//        OffersResponse offersResponse = poleEmploiService.getSpecificJobs(motsCles);
        OffersResponse offersResponse = franceTravailService.getSpecificJobs(motsCles);
        if (offersResponse.offerJobViews().isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NOT_FOUND.name(),
                    "Offers not found",
                    new PaginatedOffersResponse(
                            offersResponse.offerJobViews(),
                            offersResponse.allCoordinates()
                    ),
                    null
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<OfferJobView> pageView = PaginationUtil.paginateList(pageable, offersResponse.offerJobViews());
        ApiResponse<PaginatedOffersResponse> response = new ApiResponse<>(
                HttpStatus.OK.name(),
                "Offers and Coordinates successfully fetched",
                new PaginatedOffersResponse(
                        pageView.getContent(),
                        offersResponse.allCoordinates()
                ),
                new PageMetadata(
                        pageView.getSize(),
                        pageView.getNumber(),
                        pageView.getTotalElements(),
                        pageView.getTotalPages()
                )
        );
        return response;
    }
}
