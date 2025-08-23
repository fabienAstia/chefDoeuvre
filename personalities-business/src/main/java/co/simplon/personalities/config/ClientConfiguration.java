package co.simplon.personalities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Service
public class ClientConfiguration {

    @Bean("restClient")
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean("retryRestClient")
    public RestClient retryRestClient() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
        ClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .build();
    }

}
