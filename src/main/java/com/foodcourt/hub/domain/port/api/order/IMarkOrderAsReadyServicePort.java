package com.foodcourt.hub.domain.port.api.order;

public interface IMarkOrderAsReadyServicePort {

    void markOrderAsReady(long orderId, long employeeId);
}
