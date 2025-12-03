package com.foodcourt.hub.infrastructure.output.validation;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidateDishesPort;

import java.util.ArrayList;
import java.util.List;

public class ValidateDishesService implements IValidateDishesPort {

    private final IDishPersistencePort dishPersistencePort;

    public ValidateDishesService(IDishPersistencePort dishPersistencePort) {
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
