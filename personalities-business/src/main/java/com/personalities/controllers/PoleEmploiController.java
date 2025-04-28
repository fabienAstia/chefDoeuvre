package com.personalities.controllers;

import com.personalities.dtos.*;
import com.personalities.services.PoleEmploiService;
import com.personalities.utils.PaginationUtil;
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
public class PoleEmploiController {

    private final PoleEmploiService poleEmploiService;

    public PoleEmploiController(PoleEmploiService poleEmploiService) {
        this.poleEmploiService = poleEmploiService;
    }

//    @GetMapping("/specific")
//    public String getSpecificJobs(@RequestParam String motsCles) {
//        System.out.println("specif=" + poleEmploiService.getJobs(motsCles));
//        return poleEmploiService.getJobs(motsCles);
//    }

    @GetMapping("/paginated")
    public ApiResponse<PaginatedOffersResponse> getPaginatedOfferJobs(
            @RequestParam String motsCles,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        OffersResponse offersResponse = poleEmploiService.getSpecificJobs(motsCles);
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
        return new ApiResponse<>(
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
    }
}
