package com.liverpool.liverpooldev.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestClientConfig {
    @Value("${external.api.base-url}")
    private String baseUrl;

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
