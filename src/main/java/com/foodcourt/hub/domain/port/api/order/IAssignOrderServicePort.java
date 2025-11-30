package com.foodcourt.hub.domain.port.api.order;

public interface IAssignOrderServicePort {
    void assignOrder(long orderId, long employeeId);
}
