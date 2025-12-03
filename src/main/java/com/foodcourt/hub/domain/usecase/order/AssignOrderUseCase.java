package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.api.order.IAssignOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class AssignOrderUseCase implements IAssignOrderServicePort {

    private final IOrderPersistencePort persistencePort;
    private final IOrderTracingPersistencePort orderTracingPersistencePort;
    private final IValidationUsersPort validationUsersPort;

    public AssignOrderUseCase(IOrderPersistencePort persistencePort, IOrderTracingPersistencePort orderTracingPersistencePort, IValidationUsersPort validationUsersPort) {
        this.persistencePort = persistencePort;
        this.orderTracingPersistencePort = orderTracingPersistencePort;
        this.validationUsersPort = validationUsersPort;
    }


    @Override
    public void assignOrder(long orderId, long employeeId) {

        Order order = persistencePort.findByOrderId(orderId);
        if(order == null){
            throw  new NotFoundException(ExceptionResponse.ORDER_NOT_FOUND, Map.of("OrderId", orderId));
        }

        if(!validationUsersPort.validateEmployeeBelongsToRestaurant(order.getRestaurantId(), employeeId)){
            throw  new ForbiddenException(ExceptionResponse.INVALID_PERMISSION, Map.of("Employee id ", employeeId));
        }

        if(!order.isPending()){
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_STATUS,
                    Map.of("Current order status", order.getStatus())
            );
        }
        order.setStatus(OrderStatus.IN_PREPARATION);
        order.setAssignedEmployeeId(employeeId);
        persistencePort.saveOrder(order);
        orderTracingPersistencePort.saveTracingOrder(order);
    }
}
