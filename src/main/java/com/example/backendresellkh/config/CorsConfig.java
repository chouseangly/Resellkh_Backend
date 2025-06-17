package com.example.backendresellkh.config; // Ensure this package matches your project structure

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all paths
                        .allowedOrigins("http://localhost:3000") // Allow all origins (BE CAREFUL IN PRODUCTION)
                        // For production, change this to your frontend's specific URL, e.g., "http://localhost:3000", "https://yourfrontend.com"
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers in the request
                        .exposedHeaders("*") // Expose all headers from the response to the client (if needed)
                        .allowCredentials(true); // Do not allow credentials (like cookies) for cross-origin requests
                // Set to 'true' ONLY if your frontend relies on cookies/sessions AND you specify allowedOrigins explicitly (not '*')
            }
        };
    }
}