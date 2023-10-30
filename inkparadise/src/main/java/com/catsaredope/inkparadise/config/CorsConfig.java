package com.catsaredope.inkparadise.config;

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
                registry.addMapping("/MangaDex/**")  // Define the specific endpoints you want to allow CORS for
                        .allowedOrigins("*") // Allow requests from any origin (you can restrict this to specific origins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Define allowed HTTP methods
                        .allowedHeaders("*");  // Define allowed request headers
            }
        };
    }
}