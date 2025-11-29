package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.*;

import java.util.List;

public class ValidationUsersService implements IValidationUsersPort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IUserInfoPort userInfoPort;

    public ValidationUsersService(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IOrderPersistencePort orderPersistencePort, IUserInfoPort userInfoPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.userInfoPort = userInfoPort;
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
    public boolean clientHasPendingOrders(long clientId) {
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

    @Override
    public boolean validateEmployeeBelongsToRestaurant(long restaurantId, long employeeId) {

        long realRestaurantId = userInfoPort.getEmployeeDetails(employeeId);

        return realRestaurantId == restaurantId;
    }


}
