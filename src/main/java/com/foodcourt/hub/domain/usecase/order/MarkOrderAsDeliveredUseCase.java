package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.IMarkOrderAsDeliveredServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationOrdersPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;


public class MarkOrderAsDeliveredUseCase implements IMarkOrderAsDeliveredServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IValidationOrdersPort validationOrdersPort;

    public MarkOrderAsDeliveredUseCase(IOrderPersistencePort persistencePort, IValidationOrdersPort validationOrdersPort) {
        this.persistencePort = persistencePort;
        this.validationOrdersPort = validationOrdersPort;
    }

    @Override
    public void markOrderAsDelivered(long orderId, long employeeId, String pin) {

        Order order  = persistencePort.findByOrderId(orderId);

        if(order == null){
            throw  new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        validateOrderStatus(order);
        validateOrderPermissions(order, employeeId);
        validateSentPin(order, pin);

        order.setStatus(OrderStatus.DELIVERED);
        persistencePort.saveOrder(order);
    }

    private void validateOrderStatus(Order order ) {
        if (!validationOrdersPort.validateOrderStatusIsReady(order)) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("current order's status", order.getStatus()));
        }
    }

    private void validateOrderPermissions(Order order, long employeeId) {
        if (!validationOrdersPort.ValidateOrderIsAssignedToEmployee(order, employeeId)) {
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("This employee doesn't have the order assigned: ", employeeId));
        }
    }

    private void validateSentPin(Order order, String pin) {
       if(!order.getSecurityPin().equals(pin)){
           throw new ForbiddenException(
                   ExceptionResponse.INVALID_PIN,
                   Map.of("This PIN does not match the actual PIN: ", pin));
       }
    }

}
