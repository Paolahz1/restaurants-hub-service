package com.foodcourt.hub.infrastructure.output.jpa.adapter;

import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import com.foodcourt.hub.infrastructure.output.dto.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserVerificationRestAdapter implements IUserVerificationPort {

    private final WebClient webClient;

    @Override
    public String getUserRole(Long id) {
        RoleResponse response = webClient.get()
                .uri("users/{id}/role", id)
                .retrieve()
                .bodyToMono(RoleResponse.class)
                .block();
        return response.getRole();
    }
}
