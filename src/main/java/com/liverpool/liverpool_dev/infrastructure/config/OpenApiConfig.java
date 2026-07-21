package com.liverpool.liverpool_dev.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 3.0 documentation configuration.
 * Swagger UI is accessible at /swagger-ui.html
 * API spec is available at /api-docs
 */
@Configuration
public class OpenApiConfig {

    /**
     * Defines the OpenAPI specification metadata for the Liverpool Orders API.
     *
     * @return the configured OpenAPI object
     */
    @Bean
    public OpenAPI liverpoolOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Liverpool Orders Management API")
                        .version("1.0.0")
                        .description("Backend REST API for the Liverpool order management system. "
                                + "Built with Hexagonal Architecture (Ports & Adapters pattern). "
                                + "Provides CRUD operations for customers and delivery addresses, "
                                + "and flexible type-ahead search for orders and products.")
                        .contact(new Contact()
                                .name("Liverpool Backend Team")
                                .email("backend@liverpool.com.mx"))
                        .license(new License()
                                .name("Private")
                                .url("https://www.liverpool.com.mx")));
    }
}
