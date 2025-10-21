package com.pikolinc.meliecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger / OpenAPI documentation.
 * <p>
 * This class defines a custom OpenAPI bean that provides metadata for
 * the API documentation, including title, version, description, and contact information.
 * </p>
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom OpenAPI bean used by SpringDoc to generate API documentation.
     *
     * @return an {@link OpenAPI} instance containing metadata about the API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MELI-ecommerce API")
                        .version("v1.3.1")
                        .description("Spring Boot REST API for managing Clients, Items, and Orders")
                        .contact(new Contact()
                                .name("Daniel Vargas")
                                .email("Kas4developer@gmail.com")
                                .url("https://kaserola4.github.io/")
                        )
                );
    }
}
