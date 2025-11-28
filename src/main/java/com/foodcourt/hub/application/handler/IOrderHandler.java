package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;

public interface IOrderHandler {
    CreateOrderResponse createOrder(CreateOrderCommand command, long clientId);
}
