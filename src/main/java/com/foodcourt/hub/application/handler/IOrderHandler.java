package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.application.dto.order.GetPageOrdersCommand;
import com.foodcourt.hub.application.dto.order.GetPageOrdersResponse;

public interface IOrderHandler {
    CreateOrderResponse createOrder(CreateOrderCommand command, long clientId);
    GetPageOrdersResponse getPageOrders(GetPageOrdersCommand command, long employeeId);
}
