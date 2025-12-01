package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.order.*;
import com.foodcourt.hub.application.mapper.order.ICreateOrderMapper;
import com.foodcourt.hub.application.mapper.order.IPageOrdersMapper;
import com.foodcourt.hub.application.mapper.order.OrderToTracingOrderMapper;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.port.api.order.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    private final ICreateOrderMapper mapper;
    private final IPageOrdersMapper pageOrdersMapper;
    private final OrderToTracingOrderMapper toTracingOrderMapper;

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
    public GetTracingOrderByClientResponse getTracingOrderByClient(long orderId, long clientId) {
        List<Order> orders = getTracingOrderServicePort.getTracingOrder(orderId, clientId);
        List<TracingOrderResponse> tracingOrderResponses = toTracingOrderMapper.toTracingOrderResponseList(orders);


        return GetTracingOrderByClientResponse.builder()
                .id(orderId)
                .restaurantId(orders.get(0).getRestaurantId())
                .tracingOrder(tracingOrderResponses)
                .build();
    }

}
