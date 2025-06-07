package co.simplon.personalities.services;

import co.simplon.personalities.dtos.CoordinatesView;
import co.simplon.personalities.dtos.reversegeocoding.GeoapifyAddress;
import co.simplon.personalities.dtos.reversegeocoding.GeoapifyResponseWrapper;
import co.simplon.personalities.dtos.reversegeocoding.GeoapifyJobResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static co.simplon.personalities.utils.CoordinatesCleaner.fillMissingCoordinates;

@Service
public class ReverseGeocodingService {

    private final RestClient restClient;

    @Value("${Geoapify.token}")
    private String token;

    public ReverseGeocodingService(RestClient restClient) {
        this.restClient = restClient;
    }

    private GeoapifyResponseWrapper wrapper = new GeoapifyResponseWrapper();
    private int count;

    public List<GeoapifyAddress> getAddressesChunk(List<CoordinatesView> coordinatesChunk) {
        GeoapifyJobResponse jobResponse = createBatchJob(coordinatesChunk);

        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(() -> {
            wrapper = resultResponse(jobResponse);
            count++;
            System.out.println("WRAPPER=" + wrapper);

            if (wrapper.getStatusCode().isSameCodeAs(HttpStatus.OK) || count >= 10) {
                scheduled.close();
            }
        }, 3, 3, TimeUnit.SECONDS);

        return wrapper.getAddresses();
    }

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

    public GeoapifyResponseWrapper resultResponse(GeoapifyJobResponse jobResponse) {
        try {
            List<GeoapifyAddress> addresses = restClient.get()
                    .uri(jobResponse.url() +
                            "&format=json")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GeoapifyAddress>>() {
                    });
            wrapper.setAddresses(addresses);
            wrapper.setStatusCode(HttpStatus.OK);
        } catch (Exception e) {
            wrapper.setAddresses(Collections.emptyList());
            wrapper.setStatusCode(HttpStatus.ACCEPTED);
        }
        System.out.println("addressesChunk" + wrapper.getAddresses().toString());
        return wrapper;
    }
}
