package com.foodcourt.hub.infrastructure.output.rest.adapter;

import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.output.rest.dto.EmployeeDetailsResponse;
import com.foodcourt.hub.infrastructure.output.rest.dto.RoleResponse;
import com.foodcourt.hub.infrastructure.security.TokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserInfoRestAdapter implements IUserInfoPort {

    private final WebClient userServiceWebClient;
    private final TokenProviderService tokenProvider;


    @Override
    public long getEmployeeDetails(Long id) {
        String token = tokenProvider.getToken();
        EmployeeDetailsResponse response = userServiceWebClient.get()
                .uri("/users/info/employee/{id}", id)
                .header("Authorization", "Bearer " + token )
                .retrieve()
                .bodyToMono(EmployeeDetailsResponse.class)
                .block();
        return response.getRestaurantId();
    }
}
