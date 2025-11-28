package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.api.dish.IGetPageDishesServicePort;
import com.foodcourt.hub.domain.port.api.dish.IUpdateDishServicePort;
import com.foodcourt.hub.domain.port.api.dish.IUpdateStateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.domain.usecase.CreateDishUseCase;
import com.foodcourt.hub.domain.usecase.GetPageDishesUseCase;
import com.foodcourt.hub.domain.usecase.UpdateDishUseCase;
import com.foodcourt.hub.domain.usecase.UpdateStateDishUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DishBeanConfiguration {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IValidationUsersPort validationPort;

    @Bean
    public ICreateDishServicePort createDishServicePort(){
        return new CreateDishUseCase(dishPersistencePort, restaurantPersistencePort);
    }

    @Bean
    public IUpdateDishServicePort updateDishServicePort(){
        return new UpdateDishUseCase(dishPersistencePort, validationPort);
    }

    @Bean
    public IUpdateStateDishServicePort updateStateDishServicePort(){
        return  new UpdateStateDishUseCase(dishPersistencePort, validationPort);
    }

    @Bean
    public IGetPageDishesServicePort getPageDishesServicePort(){
        return new GetPageDishesUseCase(dishPersistencePort);
    }
}
