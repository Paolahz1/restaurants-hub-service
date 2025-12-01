package com.foodcourt.hub.domain.port.api.order;

import com.foodcourt.hub.domain.model.Order;

import java.util.List;

public interface IGetTracingOrderServicePort {

    List<Order> getTracingOrder(long orderId, long clientId);

}
