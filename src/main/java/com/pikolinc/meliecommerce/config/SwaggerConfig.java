package com.pikolinc.meliecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("MELI-ecommerce API")
                        .version("v1.1.0")
                        .description("Spring Boot REST API for managing Clients, Items and Orders")
                        .contact(new Contact()
                                .name("Daniel Vargas")
                                .email("Kas4developer@gmail.com")
                                .url("https://kaserola4.github.io/")
                        )
                );
    }
}
