package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.User;
import com.foodcourt.hub.domain.port.api.order.IMarkOrderAsReadyServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSenderPort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;
import java.util.Random;

public class MarkOrderAsReadyUseCase implements IMarkOrderAsReadyServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;
    private final ISmsSenderPort smsSender;
    private final IUserInfoPort userInfoPort;

    private static final Random random = new Random();

    public MarkOrderAsReadyUseCase(IOrderPersistencePort persistencePort, ISmsSenderPort smsSender, IOrderTracingPersistencePort orderTracingPersistencePort, IUserInfoPort userInfoPort) {
        this.persistencePort = persistencePort;
        this.smsSender = smsSender;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
        this.userInfoPort = userInfoPort;
    }

    @Override
    public void markOrderAsReady(long orderId, long employeeId ) {

        Order order  = persistencePort.findByOrderId(orderId);
        User client =  userInfoPort.getUserById(order.getClientId());

        if(order == null){
            throw  new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        validateOrderPermissions(order, employeeId);
        validateOrderStatus(order);

        order.setStatus(OrderStatus.READY);
        String pin = generateSecurityPin();

        smsSender.sendTheSecurityPin(pin, client.getPhoneNumber());
        order.setSecurityPin(pin);
        persistencePort.saveOrder(order);

        orderTracingPersistencePort.saveTracingOrder(order);
    }

    private void validateOrderPermissions(Order order, long employeeId) {
        if (!order.isAssignedTo(employeeId)) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("This employee doesn't have the order assigned: ", employeeId));
        }
    }

    private void validateOrderStatus(Order order ) {
        if (!order.isInPreparation()) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("current order's status", order.getStatus()));
        }
    }

    private String generateSecurityPin() {
        return String.format("%04d", random.nextInt(10000));
    }
}
