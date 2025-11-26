package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationPort;

public class ValidationService implements IValidationPort
{
    private  final IDishPersistencePort dishPersistencePort;
    private  final IRestaurantPersistencePort restaurantPersistencePort;

    public ValidationService(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }


    @Override
    public boolean validateOwnerOfRestaurant(Long ownerId, Long restaurantId) {
        Restaurant restaurant = restaurantPersistencePort.findById(restaurantId);
        return restaurant.getOwnerId().equals(ownerId);
    }

    @Override
    public boolean validateOwnerOfDish(Long ownerId, Long dishId) {
        Dish dish = dishPersistencePort.findByID(dishId);
        Long idRestaurant = dish.getRestaurantId();
        return validateOwnerOfRestaurant(ownerId, idRestaurant);
    }

}
