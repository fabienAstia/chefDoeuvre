package co.simplon.personalities.services;

import co.simplon.personalities.dtos.OffersResponse;
import co.simplon.personalities.dtos.francetravail.FranceTravailResponse;
import co.simplon.personalities.dtos.francetravail.TokenResponse;
import co.simplon.personalities.exceptions.OffersJobsResponseException;
import co.simplon.personalities.exceptions.TokenRetrievalException;
import co.simplon.personalities.mappers.OffersResponseMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static java.util.Objects.isNull;

@Service
public class FranceTravailService {

    @Value("${francetravail.url}")
    private String baseUrlAccessToken;

    @Value("${francetravail.client.id}")
    private String clientId;

    @Value("${francetravail.secret.key}")
    private String secretKey;

    @Value("${francetravail.offersJobs.uri}")
    private String offersJobsUri;

    public String getAccessToken() {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrlAccessToken)
                .build();

        String body = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + secretKey +
                "&scope=api_offresdemploiv2 o2dsoffre";

        TokenResponse response;
        try {
            response = restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("realm", "partenaire")
                            .build())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .body(TokenResponse.class);
        } catch (Exception e) {
            throw new TokenRetrievalException("Failed to retrieve token: " + e.getMessage(), e);
        }

        if (isNull(response) || !response.isValid()) {
            throw new TokenRetrievalException("Token response or access token is null");
        }
        return response.accessToken();
    }

    public OffersResponse getSpecificJobs(String specificJob) {
        String token = getAccessToken();
        RestClient restClient = RestClient.builder()
                .baseUrl(offersJobsUri)
                .build();

        FranceTravailResponse franceTravailResponse;
        try {
            franceTravailResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("motsCles", specificJob)
                            .queryParam("range", "0-149")
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(FranceTravailResponse.class);
        } catch (Exception e) {
            throw new OffersJobsResponseException("Failed to fetch OffersJobs: " + e.getMessage(), e);
        }

        if (isNull(franceTravailResponse)) {
            throw new OffersJobsResponseException("OffersJobs response is null");
        }
        return OffersResponseMapper.mapToOffersResponse(franceTravailResponse);
    }
}
