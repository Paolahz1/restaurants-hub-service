package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;

public class CreateDishUseCase implements ICreateDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;


    public CreateDishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort ) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;

    }

    @Override
    public void create(Dish dish, Long ownerId) {

       Long responseId = restaurantPersistencePort.getOwnerIdByRestaurant(dish.getRestaurantId());

       if(!responseId.equals(ownerId)){
           throw new InvalidPermissionException();
       }

       dishPersistencePort.saveDish(dish);

    }

}
