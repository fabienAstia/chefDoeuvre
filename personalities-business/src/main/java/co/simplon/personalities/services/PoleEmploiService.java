//package co.simplon.personalities.services;
//
//import co.simplon.personalities.dtos.OffersResponse;
//import co.simplon.personalities.dtos.poleemploi.FranceTravailResponse;
//import co.simplon.personalities.mappers.OffersResponseMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//
//import java.util.Collections;
//import java.util.Map;
//
//import static java.util.Objects.isNull;
//
//@Service
//public class PoleEmploiService {
//
//    public static final String MOTS_CLES = "?motsCles=";
//    private final RestClient restClient;
//
//    @Value("${pole.emploi.uri}")
//    private String uri;
//
//    @Value("${pole.emploi.token.uri}")
//    private String tokenUri;
//
//    @Value("${pole.emploi.client.id}")
//    private String clientId;
//
//    @Value("${pole.emploi.secret.key}")
//    private String secretKey;
//
//    public PoleEmploiService(RestClient restClient) {
//        this.restClient = restClient;
//    }
//
//    public String getAccessToken() {
//        String body = "grant_type=client_credentials" +
//                "&client_id=" + clientId +
//                "&client_secret=" + secretKey +
//                "&scope=o2dsoffre api_offresdemploiv2";
//
//        @SuppressWarnings("unchecked")
//        Map<String, Object> response = restClient.post()
//                .uri(tokenUri)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .body(body)
//                .retrieve()
//                .body(Map.class);
//        return (String) response.get("access_token");
//    }
//
//    public OffersResponse getSpecificJobs(String motsCles) {
//        String token = getAccessToken();
//
//        FranceTravailResponse poleEmploiResponse = restClient.get()
//                .uri(uri + MOTS_CLES + motsCles)
//                .header("Authorization", "Bearer " + token)
//                .header("TypeAuth", "apiKey")
//                .retrieve()
//                .body(FranceTravailResponse.class);
//        if (isNull(poleEmploiResponse)) {
//            return new OffersResponse(
//                    Collections.emptyList(),
//                    Collections.emptyList()
//            );
//        }
//        return OffersResponseMapper.mapToOffersResponse(poleEmploiResponse);
//    }
//
//}
