package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;

import java.util.ArrayList;
import java.util.List;

public class ValidationOrdersService implements IValidationOrdersPort {

    private final IDishPersistencePort dishPersistencePort;

    public ValidationOrdersService(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public boolean validateDishesSameRestaurant(Long restaurantId, List<OrderItem> orderItems) {

        List<Dish> dishes = new ArrayList<>();

        for(OrderItem item: orderItems){
            dishes.add(dishPersistencePort.findByID(item.getDishId()));
        }

        return dishes.stream()
                .allMatch(dish -> dish.getRestaurantId().equals(restaurantId));
    }




}
