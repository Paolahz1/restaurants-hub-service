package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.mapper.order.ICreateOrderMapper;
import com.foodcourt.hub.application.mapper.order.IPageOrdersMapper;
import com.foodcourt.hub.application.mapper.order.OrderTracingOrderMapper;
import com.foodcourt.hub.domain.model.EmployeeEfficiency;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.api.order.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler{

    private final ICreateOrderServicePort servicePort;
    private final IGetPageOrdersServicePort getPageOrdersServicePort;
    private final IAssignOrderServicePort assignOrderServicePort;
    private final IMarkOrderAsReadyServicePort markOrderAsReadyService;
    private final IMarkOrderAsDeliveredServicePort markOrderAsDeliveredService;
    private final ICancelOrderServicePort cancelOrderServicePort;
    private final IGetTracingOrderServicePort getTracingOrderServicePort;
    private final IGetOrdersDurationForRestaurantServicePort durationForRestaurantServicePort;
    private final IGetEmployeeAverageServicePort employeeAverageServicePort;

    private final ICreateOrderMapper mapper;
    private final IPageOrdersMapper pageOrdersMapper;
    private final OrderTracingOrderMapper tracingOrderMapper;

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand command, long clientId) {

        Order order = servicePort.createOrder(mapper.toDomain(command), clientId);
        return  mapper.toResponse(order);
    }

    @Override
    public GetPageOrdersResponse getPageOrders(GetPageOrdersCommand command, long employeeId) {

        PageModel<Order> pageModel = getPageOrdersServicePort.getPageOrder(
                command.getPage(), command.getSize(), command.getStatus(), employeeId);

        return  pageOrdersMapper.toResponse(pageModel);
    }

    @Override
    public void assignOrder(long orderId, long employeeId) {
        assignOrderServicePort.assignOrder(orderId, employeeId);
    }

    @Override
    public void markOrderAsReady(long orderId, long employeeId) {
        markOrderAsReadyService.markOrderAsReady(orderId, employeeId);
    }

    @Override
    public void markOrderAsDelivered(MarkOrderAsDeliveredCommand command, long employeeId) {
        markOrderAsDeliveredService.markOrderAsDelivered(command.getOrderId(), employeeId, command.getPin());
    }

    @Override
    public void cancelOrder(long orderId, long clientId) {
        cancelOrderServicePort.cancelOrder(orderId, clientId);
    }

    @Override
    public GetTracingOrderByClientResponse getTracingOrderByClient(Long clientId, Long orderId) {
        List<Order> orders = getTracingOrderServicePort.getTracingOrder( clientId, orderId);
        List<TracingOrderResponse> tracingOrderResponses = tracingOrderMapper.toTracingOrderResponseList(orders);


        return GetTracingOrderByClientResponse.builder()
                .orderId(orderId)
                .restaurantId(orders.get(0).getRestaurantId())
                .tracingOrder(tracingOrderResponses)
                .build();
    }

    @Override
    public GetTracingOrderDurationResponse getOrderDuration(long restaurantId, long ownerId) {
        List<OrderDuration> orders = durationForRestaurantServicePort.getOrdersDuration(restaurantId, ownerId);

        return GetTracingOrderDurationResponse.builder()
                .restaurantId(restaurantId)
                .orders(orders).build();
    }


    @Override
    public GetEmployeeRankingResponse getEmployeeRanking(long restaurantId, long ownerId) {
        List<EmployeeEfficiency> efficiencies = employeeAverageServicePort.getAverageOrderTracing(restaurantId, ownerId);
        return GetEmployeeRankingResponse.builder()
                .restaurantId(restaurantId)
                .employeeEfficiencies(efficiencies)
                .build();
    }
}
