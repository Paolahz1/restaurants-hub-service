package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.ICancelOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSender;

import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class CancelOrderUseCase implements ICancelOrderServicePort {

    private final IOrderPersistencePort persistencePort;
    private final ISmsSender smsSender;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;

    public CancelOrderUseCase(IOrderPersistencePort persistencePort, ISmsSender smsSender, IOrderTracingPersistencePort orderTracingPersistencePort) {
        this.persistencePort = persistencePort;
        this.smsSender = smsSender;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
    }


    @Override
    public void cancelOrder(long orderId, long clientId) {
        Order order = persistencePort.findByOrderId(orderId);

        if (order == null) {
            throw new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        validateOrderPermissions(order, clientId);
        validateOrderStatus(order);
        order.setStatus(OrderStatus.CANCELED);
        persistencePort.saveOrder(order);
        orderTracingPersistencePort.saveTracingOrder(order);
    }


    private void validateOrderPermissions(Order order, long clientId) {
        if(order.getClientId() != clientId){
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("ClientId: ", clientId));
        }
    }

    private void validateOrderStatus(Order order) {
        if(!order.isPending()){
            smsSender.sendNotification();
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("Current status of your order ", order.getStatus().name())
            );
        }

    }

}
