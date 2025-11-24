package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import com.foodcourt.hub.domain.usecase.restaurant.CreateDishUseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DishBeanConfiguration {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserVerificationPort userVerificationPort;

    @Bean
    public ICreateDishServicePort createDishServicePort(){
        return new CreateDishUseCase(dishPersistencePort, restaurantPersistencePort, userVerificationPort);
    }
}
