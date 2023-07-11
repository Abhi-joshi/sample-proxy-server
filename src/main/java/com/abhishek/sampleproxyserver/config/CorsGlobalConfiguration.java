package com.abhishek.sampleproxyserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    private final List<String> allowedOrigins;

    public CorsGlobalConfiguration(@Value("${app.allowed-origins}") List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(this.allowedOrigins.toArray(String[]::new))
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
