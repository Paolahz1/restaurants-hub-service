package com.foodcourt.hub.domain.usecase.order;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.port.api.order.IGetTracingOrderServicePort;
import com.foodcourt.hub.domain.port.spi.IOrderTracingPersistencePort;

import java.util.List;

public class GetTracingOrderUseCase implements IGetTracingOrderServicePort {

    private final IOrderTracingPersistencePort persistencePort;

    public GetTracingOrderUseCase(IOrderTracingPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }


    @Override
    public List<Order> getTracingOrder(long orderId, long clientId) {
        return persistencePort.getTracingByClient(clientId);
    }
}
