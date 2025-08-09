package com.example.course_service.configuration;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NonNullApi
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",  // dev local Vite
                                "http://localhost:3000",  // prod Docker accessible depuis le PC
                                "http://frontend:80"      // appel interne Docker
                        )
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
