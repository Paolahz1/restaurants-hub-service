package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;


import java.util.List;

public class ValidationUsersUsersService implements IValidationUsersPort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    public ValidationUsersUsersService(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IOrderPersistencePort orderPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
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


    @Override
    public boolean hasPendingOrders(long clientId) {

        List<Order>  clientOrders = orderPersistencePort.findByClientId(clientId);
        boolean hasPendingOrders = false;

        for (Order oder: clientOrders){
            OrderStatus status =  oder.getStatus();
            if(status == OrderStatus.PENDING || status == OrderStatus.IN_PREPARATION
                    || status == OrderStatus.READY) {
                hasPendingOrders = true;
                break;
            }
        }

        return hasPendingOrders;
    }




}
