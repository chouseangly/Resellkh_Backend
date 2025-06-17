package com.example.backendresellkh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend Resell KH API")
                        .description("JWT Authentication API for Backend Resell KH")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // This applies security globally
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer") // Tells Swagger to prefix token with "Bearer "
                                        .bearerFormat("JWT")));
    }
}