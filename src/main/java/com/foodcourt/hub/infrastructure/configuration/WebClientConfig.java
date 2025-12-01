package com.foodcourt.hub.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient userServiceWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8090/users-service")
                .build();
    }

    @Bean
    public WebClient tracingServiceWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8092/tracing-service")
                .build();
    }
}
