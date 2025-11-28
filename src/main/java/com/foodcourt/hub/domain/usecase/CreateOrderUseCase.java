package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.DishesNotFromSameRestaurant;
import com.foodcourt.hub.domain.exception.HasPendingOrdersException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;

import java.time.LocalDateTime;

public class CreateOrderUseCase implements ICreateOrderServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IValidationOrdersPort validationOrdersPort;
    private final IValidationUsersPort validationUsersPort;

    public CreateOrderUseCase(IOrderPersistencePort persistencePort, IValidationOrdersPort validationOrdersPort, IValidationUsersPort validationUsersPort) {
        this.persistencePort = persistencePort;
        this.validationOrdersPort = validationOrdersPort;
        this.validationUsersPort = validationUsersPort;
    }

    @Override
    public Order createOrder(Order order, long clientId) {

        boolean hasPendingOrders = validationUsersPort.hasPendingOrders(clientId);
        if (hasPendingOrders) {
            throw new HasPendingOrdersException();
        }

        boolean fromSameRestaurant = validationOrdersPort.validateDishesSameRestaurant(order.getRestaurantId(), order.getItems());
        if(!fromSameRestaurant){
            throw new DishesNotFromSameRestaurant();
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("The order must contain at least one dish.");
        }

        order.setClientId(clientId);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return persistencePort.saveOrder(order);
    }
}
