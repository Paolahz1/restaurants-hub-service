package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.domain.service.ValidationUsersUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidationUserServiceConfiguration {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    @Bean
    public IValidationUsersPort validationPort(){
        return new ValidationUsersUsersService(dishPersistencePort, restaurantPersistencePort, orderPersistencePort);
    }
}
