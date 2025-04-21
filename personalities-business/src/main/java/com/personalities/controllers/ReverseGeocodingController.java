package com.personalities.controllers;

import com.personalities.services.ReverseGeocodingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinates")
public class ReverseGeocodingController {

    private final ReverseGeocodingService reverseGeocodingService;

    public ReverseGeocodingController(ReverseGeocodingService reverseGeocodingService) {
        this.reverseGeocodingService = reverseGeocodingService;
    }

    @GetMapping
    public String getAddress(@RequestParam String lat, @RequestParam String lon) {
        System.out.println("address=" + reverseGeocodingService.getAddress(lat, lon));
        return reverseGeocodingService.getAddress(lat, lon);
    }
}
