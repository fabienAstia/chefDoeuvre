package com.personalities.controllers;

import com.personalities.dtos.OfferJobView;
import com.personalities.dtos.poleemploi.OfferJob;
import com.personalities.services.PoleEmploiService;
import com.personalities.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class PoleEmploiController {

    private final PoleEmploiService poleEmploiService;

    public PoleEmploiController(PoleEmploiService poleEmploiService) {
        this.poleEmploiService = poleEmploiService;
    }

    @GetMapping
    public String getJobs() {
        return poleEmploiService.getJobs();
    }

    @GetMapping("/specific")
    public List<OfferJobView> getSpecificJobs(@RequestParam String motsCles) {
        System.out.println("specif=" + poleEmploiService.getSpecificJobs(motsCles));
        return poleEmploiService.getSpecificJobs(motsCles);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<OfferJobView>> getPaginatedOfferJobs(
            @RequestParam String motsCles,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        List<OfferJobView> offerJobViews = poleEmploiService.getSpecificJobs(motsCles);
        Pageable pageable = PageRequest.of(page, size);
        Page<OfferJobView> pageView = PaginationUtil.paginateList(pageable, offerJobViews);
        return ResponseEntity.ok(pageView);
    }
}
