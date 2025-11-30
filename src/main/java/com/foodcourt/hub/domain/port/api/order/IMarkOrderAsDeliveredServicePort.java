package com.foodcourt.hub.domain.port.api.order;

public interface IMarkOrderAsDeliveredServicePort {

    void markOrderAsDelivered(long orderId, long employeeId, String pin);
}
