package com.liverpool.liverpool_dev.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestClientConfig {
    
    private String baseUrl = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1";

    /**
     * application RestClient.
     *
     * @param builder auto-configured builder from Spring Boot
     * @return the configured RestClient instance
     */

    @Bean
    public RestClient restClient(final Builder builder) {
        log.info("RestClient base URL: {}", baseUrl);

        return builder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
        
    }
}
