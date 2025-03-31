package com.personalities.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class PoleEmploiService {

    public static final String MOTS_CLES = "?motsCles=";
    private final RestClient restClient;

    @Value("${pole.emploi.uri}")
    private String uri;

    @Value("${pole.emploi.token.uri}")
    private String tokenUri;

    @Value("${pole.emploi.client.id}")
    private String clientId;

    @Value("${pole.emploi.secret.key}")
    private String secretKey;

    public PoleEmploiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getAccessToken() {
        String body = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + secretKey +
                "&scope=o2dsoffre api_offresdemploiv2";

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClient.post()
                .uri(tokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(body)
                .retrieve()
                .body(Map.class);
        return (String) response.get("access_token");
    }

    public String getJobs() {
        String token = getAccessToken();

        return restClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("TypeAuth", "apiKey")
                .retrieve()
                .body(String.class);
    }

    public String getSpecificJobs(String motsCles) {
        String token = getAccessToken();

        return restClient.get()
                .uri(uri + MOTS_CLES + motsCles)
                .header("Authorization", "Bearer " + token)
                .header("TypeAuth", "apiKey")
                .retrieve()
                .body(String.class);
    }
}
