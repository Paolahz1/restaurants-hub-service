package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Order;

import java.util.List;

public interface IOrderTracingPersistencePort {
    void saveTracingOrder(Order order);
    List<Order> getTracingByClientAndOrderId(Long clientId, Long OrderId);
    List<Order> getTracingByRestaurant(long restaurantId);
}
