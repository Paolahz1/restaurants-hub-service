package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    List<Order> findByClientId(long clientId);

}
