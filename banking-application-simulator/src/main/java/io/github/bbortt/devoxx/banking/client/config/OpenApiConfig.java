package io.github.bbortt.devoxx.banking.client.config;

import org.citrusframework.openapi.OpenApiRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiRepository accountsOpenApiRepository() {
        OpenApiRepository openApiRepository = new OpenApiRepository();
        openApiRepository.setLocations(List.of("openapi/openapi.yaml"));
        return openApiRepository;
    }
}
