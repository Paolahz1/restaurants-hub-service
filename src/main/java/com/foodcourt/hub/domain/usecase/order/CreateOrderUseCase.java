package com.foodcourt.hub.domain.usecase.order;
import com.foodcourt.hub.domain.exception.DishesNotFromSameRestaurant;
import com.foodcourt.hub.domain.exception.HasPendingOrdersException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.ICreateOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;

import java.time.LocalDateTime;

public class CreateOrderUseCase implements ICreateOrderServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IValidationOrdersPort validationOrdersPort;
    private final IValidationUsersPort validationUsersPort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;

    public CreateOrderUseCase(IOrderPersistencePort persistencePort, IValidationOrdersPort validationOrdersPort, IValidationUsersPort validationUsersPort, IOrderTracingPersistencePort orderTracingPersistencePort) {
        this.persistencePort = persistencePort;
        this.validationOrdersPort = validationOrdersPort;
        this.validationUsersPort = validationUsersPort;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
    }

    @Override
    public Order createOrder(Order order, long clientId) {

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("The order must contain at least one dish.");
        }

        boolean hasPendingOrders = validationUsersPort.clientHasPendingOrders(clientId);
        if (hasPendingOrders) {
            throw new HasPendingOrdersException();
        }

        boolean fromSameRestaurant = validationOrdersPort.validateDishesSameRestaurant(order.getRestaurantId(), order.getItems());
        if(!fromSameRestaurant){
            throw new DishesNotFromSameRestaurant();
        }

        order.setClientId(clientId);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Order saved = persistencePort.saveOrder(order);
        orderTracingPersistencePort.saveTracingOrder(saved);

        return saved;
    }
}
