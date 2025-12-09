package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.exception.ForbiddenException;
import com.foodcourt.hub.domain.exception.NotFoundException;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.port.api.order.IGetTracingOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderPersistencePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.List;
import java.util.Map;

public class GetTracingOrderUseCase implements IGetTracingOrderServicePort {

    private final IOrderTracingPersistencePort persistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    public GetTracingOrderUseCase(IOrderTracingPersistencePort persistencePort, IOrderPersistencePort orderPersistencePort) {
        this.persistencePort = persistencePort;
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public List<Order> getTracingOrder(Long clientId, Long orderId) {

        Order order = orderPersistencePort.findByOrderId(orderId);
        validateClientPermissions(order, clientId);

        List<Order> orders = persistencePort.getTracingByClientAndOrderId(clientId, orderId);
        if( orders.isEmpty()){
            throw new NotFoundException(ExceptionResponse.TRACING_NOT_FOUND, Map.of("orderId:", orderId));
        }
        return orders;
    }

    private void validateClientPermissions(Order order, Long clientId){

        if(order.getClientId() != clientId){
            throw new ForbiddenException(
                    ExceptionResponse.INVALID_PERMISSION,
                    Map.of("Provided client Id", clientId));
        }

    }

}
