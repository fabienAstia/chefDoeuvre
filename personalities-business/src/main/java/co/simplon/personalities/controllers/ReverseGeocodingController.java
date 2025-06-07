package co.simplon.personalities.controllers;

import co.simplon.personalities.services.ReverseGeocodingService;
import co.simplon.personalities.dtos.CoordinatesView;
import co.simplon.personalities.dtos.reversegeocoding.GeoapifyAddress;
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
