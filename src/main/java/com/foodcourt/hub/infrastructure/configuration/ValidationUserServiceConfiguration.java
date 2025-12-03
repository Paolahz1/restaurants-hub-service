package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.spi.*;
import com.foodcourt.hub.infrastructure.output.validation.ValidationUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidationUserServiceConfiguration {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserInfoPort userInfoPort;

    @Bean
    public IValidationUsersPort validationPort(){
        return new ValidationUsersService(dishPersistencePort, restaurantPersistencePort, orderPersistencePort, userInfoPort);
    }
}
