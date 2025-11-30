package com.foodcourt.hub.domain.port.api.order;

public interface ICancelOrderServicePort {

    void cancelOrder(long orderId, long clientId);
}
