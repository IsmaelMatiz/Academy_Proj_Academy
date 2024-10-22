package com.academy.AcademyApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Permitir CORS para todos los endpoints de la API
                .allowedOrigins("http://localhost:5173")  // Origen permitido (React)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowCredentials(true);
    }
}
