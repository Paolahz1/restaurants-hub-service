package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;

import java.util.List;

public interface IValidationOrdersPort {

    boolean validateDishesSameRestaurant(Long restaurantId, List<OrderItem> orderItems);
    boolean validateOrderStatusIsPending(Order order);
    boolean validateOrderStatusIsInPreparation(Order order);
    boolean validateOrderStatusIsReady(Order order);
    boolean ValidateOrderIsAssignedToEmployee(Order order, long employeeId);
}
