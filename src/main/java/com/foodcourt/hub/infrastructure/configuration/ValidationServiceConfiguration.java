package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationPort;
import com.foodcourt.hub.domain.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidationServiceConfiguration {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    @Bean
    public IValidationPort validationPort(){
        return new ValidationService(dishPersistencePort, restaurantPersistencePort);
    }
}
