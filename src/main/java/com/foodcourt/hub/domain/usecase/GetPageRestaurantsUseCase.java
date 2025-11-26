package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.model.Restaurant;

import com.foodcourt.hub.domain.port.api.restaurant.IGetPageRestaurantsServicePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

public class GetPageRestaurantsUseCase implements IGetPageRestaurantsServicePort {

    private final IRestaurantPersistencePort persistencePort;

    public GetPageRestaurantsUseCase(IRestaurantPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Page<Restaurant> getPage(int page, int size) {
        return persistencePort.getRestaurants(page, size);
    }
}
