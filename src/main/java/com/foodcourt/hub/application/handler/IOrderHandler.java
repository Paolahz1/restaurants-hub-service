package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.*;

public interface IOrderHandler {
    CreateOrderResponse createOrder(CreateOrderCommand command, long clientId);
    GetPageOrdersResponse getPageOrders(GetPageOrdersCommand command, long employeeId);
    void assignOrder(long orderId, long employeeId);
    void markOrderAsReady(long orderId, long employeeId);
    void markOrderAsDelivered(MarkOrderAsDeliveredCommand command, long employeeId);
    void cancelOrder(long orderId, long clientId);
    GetTracingOrderByClientResponse getTracingOrderByClient(long orderId, long clientId);
    GetOrderDurationResponse getOrderDuration(long restaurantId);
    GetEmployeeRankingResponse getEmployeeRanking(long restaurantId);
}
