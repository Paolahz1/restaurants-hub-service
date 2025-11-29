package com.foodcourt.hub.infrastructure.security;

import com.foodcourt.hub.infrastructure.output.rest.dto.AuthCommand;
import com.foodcourt.hub.infrastructure.output.rest.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class TokenProviderService {

    private final WebClient webClient;

    private String cachedToken;
    private long expiresAt;

    public String getToken(){

        if(cachedToken != null && System.currentTimeMillis() < expiresAt){
            return  cachedToken;
        }

        AuthCommand command = new AuthCommand("hub-service@system.local", "1234");

        AuthResponse authResponse = webClient.post()
                .uri("/users/auth/login")
                .bodyValue(command)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        this.cachedToken = authResponse.getToken();
        this.expiresAt = System.currentTimeMillis() + 50 * 60 * 1000; // tiempo valido del token de manera interna

        return cachedToken;
    }
}
