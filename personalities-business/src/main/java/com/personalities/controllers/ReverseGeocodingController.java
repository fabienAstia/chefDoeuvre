package com.personalities.controllers;

import com.personalities.dtos.CoordinatesView;
import com.personalities.dtos.reversegeocoding.GeoapifyAddress;
import com.personalities.services.ReverseGeocodingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class ReverseGeocodingController {

    private final ReverseGeocodingService reverseGeocodingService;

    public ReverseGeocodingController(ReverseGeocodingService reverseGeocodingService) {
        this.reverseGeocodingService = reverseGeocodingService;
    }

    @PostMapping
    public List<GeoapifyAddress> getAddressesChunk(@RequestBody List<CoordinatesView> coordinatesChunk) {
        return reverseGeocodingService.getAddressesChunk(coordinatesChunk);
    }
}
