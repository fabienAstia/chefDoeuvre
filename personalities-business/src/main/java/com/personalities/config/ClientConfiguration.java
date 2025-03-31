package com.personalities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ClientConfiguration {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

}
