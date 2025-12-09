package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.User;
import com.foodcourt.hub.domain.port.api.order.ICancelOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSenderPort;

import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class CancelOrderUseCase implements ICancelOrderServicePort {

    private final IOrderPersistencePort persistencePort;
    private final ISmsSenderPort smsSender;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;
    private final IUserInfoPort userInfoPort;

    public CancelOrderUseCase(IOrderPersistencePort persistencePort, ISmsSenderPort smsSender, IOrderTracingPersistencePort orderTracingPersistencePort, IUserInfoPort userInfoPort) {
        this.persistencePort = persistencePort;
        this.smsSender = smsSender;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
        this.userInfoPort = userInfoPort;
    }


    @Override
    public void cancelOrder(long orderId, long clientId) {

        Order order = persistencePort.findByOrderId(orderId);
        User client =  userInfoPort.getUserById(clientId);

        if (order == null) {
            throw new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        validateOrderPermissions(order, clientId);
        validateOrderStatus(order, client);
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

    private void validateOrderStatus(Order order, User client) {
        if(!order.isPending()){
            smsSender.sendNotification(client.getPhoneNumber());
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("Current status of your order ", order.getStatus().name())
            );
        }

    }

}
