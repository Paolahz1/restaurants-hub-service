package com.foodcourt.hub.infrastructure.configuration;

import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import com.foodcourt.hub.domain.port.api.restaurant.IGetPageRestaurantsServicePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import com.foodcourt.hub.domain.usecase.restaurant.CreateRestaurantUseCase;
import com.foodcourt.hub.domain.usecase.restaurant.GetPageRestaurantsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestaurantBeanConfiguration {

    private final IRestaurantPersistencePort persistencePort;
    private final IUserVerificationPort userVerificationPort;


    @Bean
    public ICreateRestaurantServicePort createRestaurantServicePort() {
        return new CreateRestaurantUseCase( persistencePort,  userVerificationPort);
    }

    @Bean
    IGetPageRestaurantsServicePort getPageRestaurantsServicePort(){
        return  new GetPageRestaurantsUseCase(persistencePort);
    }

}
