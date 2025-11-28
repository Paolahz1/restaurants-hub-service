package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateOrderCommand;
import com.foodcourt.hub.application.dto.CreateOrderResponse;

public interface IOrderHandler {
    CreateOrderResponse createOrder(CreateOrderCommand command, long clientId);
}
