package com.foodcourt.hub.domain.usecase.order;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.IMarkOrderAsReadyServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.ISmsSender;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;
import java.util.Random;

public class MarkOrderAsReadyUseCase implements IMarkOrderAsReadyServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IValidationOrdersPort validationOrdersPort;
    private final ISmsSender smsSender;

    private static final Random random = new Random();

    public MarkOrderAsReadyUseCase(IOrderPersistencePort persistencePort, IValidationOrdersPort validationOrdersPort, ISmsSender smsSender) {
        this.persistencePort = persistencePort;
        this.validationOrdersPort = validationOrdersPort;
        this.smsSender = smsSender;
    }

    @Override
    public void markOrderAsReady(long orderId, long employeeId ) {

        Order order  = persistencePort.findByOrderId(orderId);

        if(order == null){
            throw  new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        validateOrderPermissions(order, employeeId);
        validateOrderStatus(order);

        order.setStatus(OrderStatus.READY);
        String pin = generateSecurityPin();

        smsSender.sendTheSecurityPin(pin);
        order.setSecurityPin(pin);
        System.out.println("EN MARKORDERASREASDY USE CASE PIN:" + pin);
        persistencePort.saveOrder(order);
    }

    private void validateOrderPermissions(Order order, long employeeId) {
        if (!validationOrdersPort.ValidateOrderIsAssignedToEmployee(order, employeeId)) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("This employee doesn't have the order assigned: ", employeeId));
        }
    }

    private void validateOrderStatus(Order order ) {
        if (!validationOrdersPort.validateOrderStatusIsInPreparation(order)) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("current order's status", order.getStatus()));
        }
    }

    private String generateSecurityPin() {
        return String.format("%04d", random.nextInt(10000));
    }
}
