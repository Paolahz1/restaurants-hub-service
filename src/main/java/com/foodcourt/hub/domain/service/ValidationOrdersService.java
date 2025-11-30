package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.OrderStatus;
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

    @Override
    public boolean validateOrderStatusIsPending(Order order) {
        return order.getStatus() == OrderStatus.PENDING;
    }

    @Override
    public boolean validateOrderStatusIsInPreparation(Order order){
        return order.getStatus() == OrderStatus.IN_PREPARATION;
    }

    @Override
    public boolean validateOrderStatusIsReady(Order order) {
        return order.getStatus() == OrderStatus.READY;
    }

    @Override
    public boolean ValidateOrderIsAssignedToEmployee(Order order, long employeeId) {
        long realEmployeeId = order.getAssignedEmployeeId();
        return realEmployeeId == employeeId;
    }


}
