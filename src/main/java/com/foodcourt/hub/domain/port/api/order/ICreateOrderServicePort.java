package com.foodcourt.hub.domain.port.api.order;

import com.foodcourt.hub.domain.model.Order;

public interface ICreateOrderServicePort {

    Order createOrder(Order order, long clientId);
}
