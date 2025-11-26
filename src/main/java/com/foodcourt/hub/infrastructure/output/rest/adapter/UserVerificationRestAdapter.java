package com.foodcourt.hub.infrastructure.output.rest.adapter;

import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import com.foodcourt.hub.infrastructure.output.rest.dto.RoleResponse;
import com.foodcourt.hub.infrastructure.output.rest.dto.UserResponse;
import com.foodcourt.hub.infrastructure.security.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserVerificationRestAdapter implements IUserVerificationPort {

    private final WebClient webClient;
    private final TokenProviderService tokenProvider;

    @Override
    public String getUserRole(Long id) {

        String token = tokenProvider.getToken();

        RoleResponse response = webClient.get()
                .uri("users/info/role/{id}", id)
                .header("Authorization", "Bearer " + token )
                .retrieve()
                .bodyToMono(RoleResponse.class)
                .block();

        return response.getRole();
    }

    @Override
    public UserResponse getUser(Long id) {
        return webClient.get()
                .uri("users/auth/{id}", id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();

    }


}
