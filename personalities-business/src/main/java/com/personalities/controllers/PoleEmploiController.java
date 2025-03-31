package com.personalities.controllers;

import com.personalities.services.PoleEmploiService;
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

    @GetMapping
    public String getJobs() {
        return poleEmploiService.getJobs();
    }

    @GetMapping("/specific")
    public String getSpecificJobs(@RequestParam String motsCles) {
        return poleEmploiService.getSpecificJobs(motsCles);
    }
}
