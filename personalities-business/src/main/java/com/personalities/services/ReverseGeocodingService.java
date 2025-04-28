package com.personalities.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personalities.dtos.CoordinatesView;
import com.personalities.dtos.reversegeocoding.GeoapifyAdressesResponse;
import com.personalities.dtos.reversegeocoding.GeoapifyJobResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.personalities.utils.CoordinatesCleaner.fillMissingCoordinates;

@Service
public class ReverseGeocodingService {

    private final RestClient restClient;

    @Value("${Geoapify.token}")
    private String token;

    public ReverseGeocodingService(RestClient restClient) {
        this.restClient = restClient;
    }

//    public GeoapifyAdressesResponse manageBatchGeocodingProcess(List<CoordinatesView> allCoordinates) {
//        GeoapifyJobResponse jobResponse = createBatchJob(allCoordinates);
//        GeoapifyAdressesResponse allAddresses = getAllAddresses(jobResponse);
//        System.out.println("allAddresses" + allAddresses);
//        return allAddresses;
//    }

//    public String getAllAddresses(List<CoordinatesView> allCoordinates) {
//        GeoapifyJobResponse jobResponse = createBatchJob(allCoordinates);
//        String allAddresses = resultResponse(jobResponse);
//        System.out.println("allAddresses" + allAddresses);
//        return allAddresses;
//    }

    public GeoapifyJobResponse createBatchJob(List<CoordinatesView> allCoordinates) {
        List<CoordinatesView> filledMissingCoordinates = fillMissingCoordinates(allCoordinates);
        return restClient.post()
                .uri("https://api.geoapify.com/v1/batch/geocode/reverse?" +
                        "&apiKey=" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(filledMissingCoordinates)
                .retrieve()
                .body(GeoapifyJobResponse.class);
    }

    //    @Retryable
    public GeoapifyAdressesResponse resultResponse(GeoapifyJobResponse responseJob) {
        ObjectMapper objectMapper = new ObjectMapper();

        return restClient.get()
                .uri(responseJob.url() +
                        "&format=json")
//                .header("TypeAuth", "apiKey")
//                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GeoapifyAdressesResponse.class);
    }

//    public String resultResponse(GeoapifyJobResponse responseJob) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        return restClient.get()
//                .uri(responseJob.url() +
//                        "&format=json")
//                .exchange((resquest, response) -> {
//                    if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
//                        return objectMapper.readValue(response.getBody(), new TypeReference<>() {
//                        });
//                    }
//                    return null;
//                });
//        //.header("TypeAuth", "apiKey")
//        //.accept(MediaType.APPLICATION_JSON)
////                .retrieve()
////                .body(String.class);
//        //.body(GeoapifyAdressesResponse.class);
//    }
}
