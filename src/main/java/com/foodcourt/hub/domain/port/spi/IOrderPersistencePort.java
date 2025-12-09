package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.model.PageModel;


import java.util.List;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    List<Order> findByClientId(long clientId);
    PageModel<Order> getPageFromDb(int page, int size, OrderStatus status, long restaurantId);
    Order findByOrderId (long orderId);
    void deleteOrder(long orderId);
}
