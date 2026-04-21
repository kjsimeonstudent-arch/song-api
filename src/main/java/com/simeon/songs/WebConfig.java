package com.simeon.songs;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Value("${ALLOWED_ORIGINS:http://localhost:5173,http://localhost:3000}")
            private String allowedOrigins;
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins(allowedOrigins.split("https://song-ui-d5fq.onrender.com"")) // Support multiple origins separated by commas
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // If using cookies or authentication headers
            }
        };
    }
}
