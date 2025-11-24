package com.foodcourt.hub.domain.usecase.restaurant;

import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;

public class CreateDishUseCase implements ICreateDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserVerificationPort userVerificationPort;

    public CreateDishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserVerificationPort userVerificationPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userVerificationPort = userVerificationPort;
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
