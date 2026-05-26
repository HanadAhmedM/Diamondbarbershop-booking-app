package com.Diamondbarberdhopapp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
        .allowedOrigins(
            "http://localhost:5173",
            "http://16.171.14.23:5173"
        )
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}