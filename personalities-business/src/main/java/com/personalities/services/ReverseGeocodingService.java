package com.personalities.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ReverseGeocodingService {

    private final RestClient restClient;

    @Value("${locationIQ.token}")
    private String token;

    public ReverseGeocodingService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getAddress(String lat, String lon) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);

        return restClient.get()
                .uri("https://eu1.locationiq.com/v1/reverse" +
                        "?key=" + token +
                        "&lat=" + latitude +
                        "&lon=" + longitude +
                        "&format=json")
                .header("Content-type", "application/json")
                .retrieve()
                .body(String.class);
    }
}
